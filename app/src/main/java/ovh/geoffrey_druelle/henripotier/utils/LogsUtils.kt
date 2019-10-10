package ovh.geoffrey_druelle.henripotier.utils

import android.util.Log
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication.Companion.appContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class LogsUtils {

    companion object {
        private val TAG = "HPLog"
        private val patternLogFile = "${TAG}_.*\\.txt"
        private val DEBUG_TAG = TAG

        private var logFileName: String = ""
        private var bLogDebugLevel1 = true

        private fun writeLine(
            prefix: HPLogType,
            module: String,
            info: String,
            trace: String
        )
        {
            val isHeader: Boolean
            var outputStreamWriter: OutputStreamWriter? = null
            try {
                // Suppress old files if exist
                val logsDirectoryPath = appContext.getExternalFilesDir(null)
                if (logsDirectoryPath == null) {
                    System.err.println("File path of logs doesn't exist / is null")
                    return
                }
                deleteLogsFiles(logsDirectoryPath)
                val fileDateFormat = SimpleDateFormat(logFileDateFormatYYYYMMDD, Locale.FRANCE)
                val fileDateTime = fileDateFormat.format(Date())
                logFileName = "$logsDirectoryPath/HPLog_$fileDateTime.txt"

                val f = File(logFileName)
                // Adding header is file doesn't exist
                isHeader = !f.exists()
                // Opening file with append
                val fileOutputStream = FileOutputStream(f, true)
                outputStreamWriter = OutputStreamWriter(fileOutputStream)

                if (isHeader) {
                    outputStreamWriter.write(
                        "\"Type\";" +
                                "\"Date and Hour\";" +
                                "\"Module\";" +
                                "\"Info\";" +
                                "\"Trace\"\n"
                    )
                }

                val dateFormat =
                    SimpleDateFormat(dateFormatYYYY_MM_DD_HH_MM_SS_SS, Locale.FRANCE)
                val dateTime = dateFormat.format(Date())

                outputStreamWriter.write(
                    ("\"" + prefix.toString() + "\";" +
                            "\"" + dateTime + "\";" +
                            "\"" + module.replace("\"", "\"\"") + "\";" +
                            "\"" + info.replace("\"", "\"\"") + "\";" +
                            "\"" + trace.replace("\"", "\"\"") + "\"\n")
                )

                outputStreamWriter.flush()

            } catch (ex: Exception) {
                // There is no error, because it is a logs file
            } finally {
                if (outputStreamWriter != null) {
                    try {
                        outputStreamWriter.close()
                    } catch (ignored: IOException) {
                    }
                }
            }
        }

        fun delete() {
            val f = File(logFileName)
            if (f.exists()) {
                f.delete()
            }
        }

        fun getLogFileName(): String? {
            return logFileName
        }

        fun setLogFileName(logFileName: String) {
            this.logFileName = logFileName
        }

        // Log a debug information
        fun d(module: String, info: String) {
            // If not authorized, stop
            if (!bLogDebugLevel1) return
            Log.d(DEBUG_TAG, "$module, $info")
            writeLine(HPLogType.Debug, module, info, "")
        }

        // Log an error #1
        fun e(module: String, info: String, ex: Throwable) {
            var exceptionDetails = ""
            try {
                exceptionDetails += ex.message
                val stackTraceElements = ex.stackTrace
                for (stackTraceElement in stackTraceElements) {
                    exceptionDetails += "|$stackTraceElement"
                }
            } catch (ex: Exception) {
                exceptionDetails = "Error lors de la récupération du détail de l'exception"
            }
            e(module, info, exceptionDetails)
        }

        // Log an error #2
        fun e(module: String, info: String, trace: String) {
            Log.e(DEBUG_TAG, "$module, $info,$trace")
            writeLine(HPLogType.Error, module, info, trace)
        }

        // Log an error #3
        fun e(module: String, ex: Exception) {
            Log.e(DEBUG_TAG, module, ex)
            e(module, "", ex)
        }

        // Log an information
        fun i(module: String, info: String) {
            writeLine(HPLogType.Info, module, info, "")
        }

        fun getLogFiles(): Array<File?> {
            val logsDirectoryPath =
                appContext.getExternalFilesDir(null) ?: return arrayOfNulls<File?>(0)

            val p = Pattern.compile(patternLogFile)

            return logsDirectoryPath.listFiles { dir, name -> p.matcher(name).matches() }
        }

        // Delete logs files
        fun deleteLogsFiles(logsDirectoryPath: File?) {
            try {
                //File cheminRepertoireLog = MaximoApplication.getInstance().getExternalFilesDir(null);
                if (logsDirectoryPath != null) {
                    deletingLogs(logsDirectoryPath.absolutePath, "HPLog_.*\\.txt", 15)
                }
            } catch (e: Exception) {
                e(
                    "$TAG.deleteLogsFiles",
                    "Fail when deleting logs files",
                    e
                )
            }

        }

        // Deleting of files in logs' folder and corresponding to a given pattern if date is
        // prior to the limit date of files' safeguard
        fun deletingLogs(
            logsDirectoryPath: String,
            logsFilePattern: String,
            logsLifetime: Int
        ) {
            if (logsLifetime == 0) return
            val folder = File(logsDirectoryPath)
            val pattern = Pattern.compile(logsFilePattern)

            val files = folder.listFiles { _, name ->
                pattern.matcher(name).matches()
            }

            if (files != null && files.isNotEmpty()) {
                for (file in files) {
                    val diff = Date().time - file.lastModified()
                    if (diff > logsLifetime * 24 * 60 * 60 * 1000) {
                        if (!file.delete()) {
                            System.err.println("Can't remove " + file.absolutePath)
                        }
                    }
                }
            }
        }

        enum class HPLogType(val logType: Char) {
            Error('E'),
            Warning('W'),
            Info('I'),
            Debug('D'),
        }
    }
}
package ovh.geofrey_druelle.henripotier

import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ovh.geoffrey_druelle.henripotier.HenriPotierApplication
import ovh.geoffrey_druelle.henripotier.data.model.Offer
import ovh.geoffrey_druelle.henripotier.data.model.Offers
import ovh.geoffrey_druelle.henripotier.injection.getModules
import ovh.geoffrey_druelle.henripotier.ui.cart.CartViewModel

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = HenriPotierApplication::class)
class CartViewModelUnitTest : KoinTest {

    private val cartViewModel by inject<CartViewModel>()

    private var priceBeforeReduces: MutableLiveData<Int> = MutableLiveData(0)
    private var totalReduces: MutableLiveData<Int> = MutableLiveData(0)


    @Before
    fun setUp() {
        stopKoin()
        startKoin {
            modules(getModules())
        }

        priceBeforeReduces.value = 100
        totalReduces.value = 10
    }

    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun percentageValueTest() {
        assertEquals(15, cartViewModel.percentageValue(15, priceBeforeReduces))
    }

    @Test
    fun sliceValueTest() {
        assertEquals(10, cartViewModel.sliceValue(Offer(50, "slice", 5), priceBeforeReduces))
        assertEquals(25, cartViewModel.sliceValue(Offer(100, "slice", 25), priceBeforeReduces))
    }

    @Test
    fun fixPriceAfterReducesTest() {
        assertEquals(90, cartViewModel.fixPriceAfterReduces(totalReduces, priceBeforeReduces))
    }

    @Test
    fun parseOfferTest() {
        assertEquals(
            5, cartViewModel.parseOffer(
                Offer(0, "percentage", 5), priceBeforeReduces
            )
        )
        assertEquals(
            5, cartViewModel.parseOffer(
                Offer(0, "minus", 5), priceBeforeReduces
            )
        )
        assertEquals(
            10, cartViewModel.parseOffer(
                Offer(50, "slice", 5), priceBeforeReduces
            )
        )
        assertEquals(
            25, cartViewModel.parseOffer(
                Offer(100, "slice", 25), priceBeforeReduces
            )
        )
    }

    @Test
    fun findBestOfferTest(){
        val offers = Offers(listOf(
            Offer(sliceValue=0, type="percentage", value=5),
            Offer(sliceValue=0, type="minus", value=15),
            Offer(sliceValue=100, type="slice", value=12))
        )

        assertEquals(15,cartViewModel.findBestOffer(offers, priceBeforeReduces))

        val offers2 = Offers(listOf(
            Offer(sliceValue=0, type="percentage", value=5),
            Offer(sliceValue=0, type="minus", value=3),
            Offer(sliceValue=100, type="slice", value=30))
        )
        assertEquals(30,cartViewModel.findBestOffer(offers2, priceBeforeReduces))

        val offers3 = Offers(listOf(
            Offer(sliceValue=0, type="percentage", value=50),
            Offer(sliceValue=0, type="minus", value=15),
            Offer(sliceValue=100, type="slice", value=30))
        )
        assertEquals(50,cartViewModel.findBestOffer(offers3, priceBeforeReduces))
    }
}
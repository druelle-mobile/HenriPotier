# Henri Potier

This project is my personal interpretation of a Xebia's exercise in a 
recruitment's process. as Android Developper.

## Statement

> Once upon a time, there was a collection of **seven** books telling the 
stories of an amazing hero named Henri Potier.
All the children of the world found fantastic the stories of this teenager.
The publisher of this collection, in a huge surge of generosity 
(but also to boost his sales ;) ), decided to set up commercial offers 
as random as the issue of Ron Weasley's spells.

The publisher asks you to develop an Android or iOS mobile application
 with two interfaces:

1. The first is to display the books you want to buy;
1. The second summarizes the basket on which **the best commercial
 offer** will be applied.

You must know that the publisher will pay special attention to
 **the quality of developments**.

## Ressources

The list of Henri Potier books is available at 
`http://henri-potier.xebia.fr/books` with `GET`.

The associated commercial offers are available in `GET` at the following address: 
`http://henri-potier.xebia.fr/books/{ISBN1, ISBN2, ...}/commercialOffers`

## Example

For two books (respectively €35 and €30), the query will look like: 
`http://henri-potier.xebia.fr/books/c8fabf68-8374-48fe-a7ea-a00ccd07afff,a460afed-e5e7-4e39-a39d -c885c05db861/commercialOffers`

The service will return the offers applicable to this basket in `JSON`:

```json
{
  "offers": [
    { "type": "percentage", "value": 5 },
    { "type": "minus", "value": 15 },
    { "type": "slice", "sliceValue": 100, "value": 12 }
  ]
}
```

The expected price for this basket will be €50.

#### Explanations

* The first offer identified by a `percentage` type is a discount applying to
the price of all the books.
The amount of the reduction is in `value`;

* The second offer identified by a `minus` type is a deduction, directly applicable
to checkout of an amount of `value`;

* The third offer identified by a `slice` type is a refund per purchase slice.
In this example, we pay €12 per €100 purchase.

*Beyond the "imposed exercise", any additional original ideas will be appreciated.*
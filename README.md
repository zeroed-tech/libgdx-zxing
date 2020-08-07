# Libgdx-ZXing

This library is a LibGDX wrapper around the QR generation functionality of the Zebra Crossing Barcode library https://github.com/zxing/zxing.

The library uses zxing to generate a QR code bit matrix then uses https://github.com/earlygrey/shapedrawer based to render the QR code to a framebuffer. Finally, the texture is extracted from the frame buffer and returned.

# Examples
![Example](https://github.com/zeroed-tech/libgdx-zxing/raw/master/Example.png)

# Setup
Nothing special here, just import from Jitpack:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
``` 
```groovy
project(":core") {
    ...
    dependencies {
        implementation 'com.github.zeroed-tech:libgdx-zxing:master-SNAPSHOT'
    }
}
```

# Terminology
I haven't read the QR spec so I've almost certainly gotten the terminology wrong in the code. Here is what I've referred to everything as:

* Block - a single square of the QR code
* Eye - the large (7x7) squares in the top left and right and the bottom left corners 

# Usage
Start by creating a QRGenerator and specifying a block size (the number of pixels each square should take up:
```java
QRGenerator generator = new QRGenerator(15)
```

Configure your generator
```java
// Change you block size after you forgot to set it in the constructor
generator.blockSize(10)

// Change the border size. The border is the number of block of white space should be placed around your QR code (0-1 is usually enough)
generator.borderSize(1)

// Set the primary (block) and secondary (background) colours
generator.primaryColor(Color.Black)
generator.secondaryColor(Color.White)

// Set the eye border shape (ARC, CIRCLE or SQUARE)
generator.setEyeBorderShape(QRGenerator.Shape.ARC)

// Set the blocks inside the eyes shape (CIRCLE or SQUARE)
generator.setEyeInnerShape(QRGenerator.Shape.CIRCLE)

// Set the blocks shape (CIRCLE or SQUARE)
generator.setInnerShape(QRGenerator.Shape.SQUARE)
```

Generate your QR code
```java

// Generate your QR code
TextureRegion code = generator.generate("Zeroed.tech")

// Do things with your code, don't forget to dispose of it when you're done
```

## Some example setups
```java
String input = "Zeroed.tech"
 // Generate a basic QR code
new QRGenerator(12).generate(input)

// Generate a QR code with arcs on the eye borders
new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).generate(input)

// Generate a QR code with arcs on the eye borders and circular inner bits
new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).setEyeInnerShape(QRGenerator.Shape.CIRCLE).generate(input)

// Generate a QR code with arcs on the eye borders and circular everything else
new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).setEyeInnerShape(QRGenerator.Shape.CIRCLE).setInnerShape(QRGenerator.Shape.CIRCLE).generate(input)

// Generate a QR code where everything is a circle
new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.CIRCLE).setEyeInnerShape(QRGenerator.Shape.CIRCLE).setInnerShape(QRGenerator.Shape.CIRCLE).generate(input)

// Change up the colors
new QRGenerator(12).primaryColor(Color.WHITE).secondaryColor(Color.BLACK).generate(input)
new QRGenerator(12).primaryColor(Color.GREEN).secondaryColor(Color.BLACK).generate(input)

// Generate a QR code with a larger border
new QRGenerator(12).borderSize(3).generate(input)

// Generate a larger QR code
new QRGenerator(20).generate(input)
```
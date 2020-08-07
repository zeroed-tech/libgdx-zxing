# Libgdx-ZXing

This library is a LibGDX wrapper around the QR generation functionality of the Zebra Crossing Barcode library https://github.com/zxing/zxing.

The library uses zxing to generate a QR code bit matrix then uses https://github.com/earlygrey/shapedrawer based to render the QR code to a framebuffer. Finally, the texture is extracted from the frame buffer and returned.

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

// Generate your QR code
TextureRegion code = generator.generate("Zeroed.tech")

// Do things with your code, don't forget to dispose of it when you're done
```
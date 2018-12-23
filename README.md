# StencylSimple Share via e-mail, Twitter, Facebook etc. Extension (Openfl)

For Stencyl 3.4 and above

Stencyl extension for “Simple Share” on iOS and Android. This extension allows you to easily integrate Simple Share via e-mail, Twitter, Facebook etc. on your Stencyl game / application. (http://www.stencyl.com)

You can share a message with a website and with an image, via all existing apps on a mobile device. This extension use the IMAGE API to share an image.

<span style="color:red;">The website or screenshot will share with Facebook and not the message. All other apps works fine..</span><br/>
It seems that a recent update to the Facebook application has replaced the in-built Facebook share activity with one that ignores status text .

Facebook's policies don't allow you to pre-populate status messages and require all content to be user generated - while I understand the intention behind this, I personally think it is kind of stupid in many cases - For example in my game I want to pre-populate the user's score, but now I can't, so the user is presented with an empty dialog box. I will probably simply remove the Facebook sharing option as no-one will ever use it now.<br/>
https://developers.facebook.com/docs/apps/review/prefill

### Important!!

This Extension Required the Toolset Extension Manager [https://byrobingames.github.io](https://byrobingames.github.io)

![simplesharetoolset](https://byrobingames.github.io/img/simpleshare/simplesharetoolset.png)

## Main Features

- Simple share with 1 button click.
- Share Screenshot on IOS and Android
- Return true or false (android always return true)
- 64-bit support to iOS
- Tested with iPhone 5 IOS 8, Ipad IOS 8 and Samgsung Note 3 4.4.2

## How to Install

To install this Engine Extension, go to the toolset (byRobin Extension Mananger) in the Extension menu of your game inside Stencyl.<br/>
![toolsetextensionlocation](https://byrobingames.github.io/img/toolset/toolsetextensionlocation.png)<br/>
Select the Extension from the menu and click on "Download"

If you not have byRobin Extension Mananger installed, install this first.<br/>
Go to: [https://byrobingames.github.io](https://byrobingames.github.io)


## Screenshots
![iosscreenshot](https://byrobingames.github.io/img/simpleshare/simpleshareios.png)![androidscreenshot](https://byrobingames.github.io/img/simpleshare/simpleshareandroid.png)

## Documentation and Blocks Example

Just put the block under a share button, fill in a message with a website url and set yes or no if you wanna share a screenshot. Website has to start with http://
Select yes or no if you wanna share a screenshot.

<hr/>

### Share Message with website
Send a message with website url to other app like facebook/twitter/email<br/>
![sharecontent](https://byrobingames.github.io/img/simpleshare/sharecontent.png)

**Inputs**
- Message(as Text)
- Website(as Text) start with http://

<hr/>

### Share Message with website and with image
Send a message with website url  and with an image to other app like facebook/twitter/email<br/>
![sharewithimage](https://byrobingames.github.io/img/simpleshare/sharewithimage.png)

**Inputs**

- Message(as Text)
- Website(as Text) start with http://
- With image

If you want to share an image, use the build-in Image API blocks like “current sceen as image” for screenshot or “image from Actor” for an image of an Actor.<br/>
You als can share an personal image of your choose, just use the “Image from file” block<br/>
![imageApi](https://byrobingames.github.io/img/simpleshare/imageApi.png)

<hr/>

**Callback**
If Share Message succeed or failed you can check if share succeed or failed.<br/>
<span style="color:red;">On Android it will always return true even when share is failed.</span><br/>
![simplesharecallback](https://byrobingames.github.io/img/simpleshare/simplesharecallback.png)

## Version History

- 2015-02-13 (1.0) : first release
- 2015-02-25 (1.1) : update Share with screenshot on IOS
- 2015-03-02 (1.2) : update Share with screenshot on Android
- 2015-03-07 (1.3) : update Return true of false if share succeed of failed. (On Android always return true even if share failed).
- 2015-03-12 (1.4) : FIX: share screenshot in landscape in IOS, IOS now also use the IMAGE API to take a screenshot.
- 2015-03-22 (1.5) : FIX:  game crashed on iPad when share with screenshot, is fixed now.
- 2015-05-16 (1.6) : Android: Save screenshot to phone storage
- 2015-06-13 (1.7) Update info.txt file.
- 2015-07-31 (1.8) Add icon on blocks
- 2016-04-09(1.9.0) You can now share image by using the build-in IMAGE API blocks. Created an extra block.
- 2016-09-29(1.9.1) Added extra permission for iOS 10, so app will not crash, extension requires byRobin Extension Manager
- 2017-03-19(1.9.2) Added Android Gradle support for openfl4
- 2017-05-16(1.9.3) Tested for Stencyl 3.5, Required byRobin Toolset Extension Manager
- 2017-05-18(1.9.4) Fix: Crash on some iOS devices
- 2017-06-03(1.9.5) Fix: Crash on iOS when use with Unityads

## Submitting a Pull Request

This software is opensource.<br/>
If you want to contribute you can make a pull request

Repository: [https://github.com/byrobingames/simpleshare](https://github.com/byrobingames/simpleshare)

Need help with a pull request?<br/>
[https://help.github.com/articles/creating-a-pull-request/](https://help.github.com/articles/creating-a-pull-request/)

## ANY ISSUES?

Add the issue on GitHub<br/>
Repository: [https://github.com/byrobingames/simpleshare/issues](https://github.com/byrobingames/simpleshare/issues)

Need help with creating a issue?<br/>
[https://help.github.com/articles/creating-an-issue/](https://help.github.com/articles/creating-an-issue/)

## Donate

[![paypal](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HKLGFCAGKBMFL)<br />

## License

Author: Robin Schaafsma

The MIT License (MIT)

Copyright (c) 2014 byRobinGames [http://www.byrobin.nl](http://www.byrobin.nl)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

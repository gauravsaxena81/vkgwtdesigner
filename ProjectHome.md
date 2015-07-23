## VkGWT Designer ##
NB: To comment please go to [wiki](http://code.google.com/p/vkgwtdesigner/wiki/Project_Discussion)
### Vision: ###
1.The product should design applications in browser. The real power of VkGWT Designer lies in the fact that new UIs can be made / edited / deleted without having to compile the code. The generated UI can be saved as json and from the same json they can be remade along with the event handling code. Thus, it can be used as a hosted service as well.

2.VkGWT Designer should be extendable. When VkGWTDesigner is used as a plugin, it offers easy interfaces to extend its functionalities. Thus newer components can be added by adding a few classes and attributes of existing components can be exposed or hidden.

### Demo(Mozilla FF only) ###

Do have a look at the first [demo](http://vkgwtdesigner.googlecode.com/svn/trunk/VkGwtDesigner/war/index.html) and please provide your comments at the [wiki](http://code.google.com/p/vkgwtdesigner/wiki/Project_Discussion) and bugs at the [issues list](https://code.google.com/p/vkgwtdesigner/issues/list)

#### Instructions ####

1. Right click for options for each widget

2. To add javascript for event handlers,

> a. click on a widget, to popoulate its id in the textarea e.g. (&4)

> b. Treat this id as element and invoke javascript functions or attributes e.g. (&4).innerHTML = new Date();

3. Style dialog applies styles as and when it changes to give a WYSIWYG feel.

### Goals: ###
http://code.google.com/p/vkgwtdesigner/wiki/Project_Discussion
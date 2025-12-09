# Quick Android Application Activity


Suppose we need a simple Android app to display a grid of images:
1. Use the "empty activity" template to get started (or your already set up project with dependencies).
2. Use the site https://picsum.photos to fetch a list of images from their API ( https://picsum.photos/v2/list ).
 * Add a dynamic query parameter for "limit" that can be easily adjusted from callers.
3. Provide a UI that will do the following:
 * Display a grid of images.
 * Provide details (author, id) about the picture when clicking on it.
 * Sort images by author.

Because this is a brief exercise and not a full fledged app, you can make the following assumptions:
 * Persisting data across multiple app launches is not required.
 * Refreshing the data while the app is running is not required.

Additional instructions:
 1. We know making a great-looking UI takes a lot longer than we have time for. Your UI should be functional and responsive, but is not expected to be attractive. You will be judged on the quality of your code, not the style of your UI.
 2. The app should not crash under any circumstance, but robust error handling is not necessary. Logging the exception is sufficient.
 3. Consider what would happen if the user's network speed is slow or has a low powered / old device.
 4. Feel free to use any public third-party libraries.
 5. Feel free to use google, stack overflow, etc. AI tools are welcome for simple lookups like syntax.
   Lorem Picsum
   Lorem Ipsum... but for photos
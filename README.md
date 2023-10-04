

To run this project,

   1. place the GIANT BOMB API KEY into ~/.giant-bomb-key
   2. $ ./run.sh
   3. browse to http://localhost:7878/
   
   For a limited time, it will also be hosted at http://jadn.com:7878/
   
   
# This Project has Two Parts

## Backend

A Clojure web server for serving the site files, and a proxy for reaching the giant bomb api.

The backend is only 2 files;
   deps.end
   proxy-src/web_server.clj

## Frontend

A ClojureScript application deployed into the public directory.


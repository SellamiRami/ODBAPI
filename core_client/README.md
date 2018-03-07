# ODB API Client
This small client can call basic operations supported by ODB API and visualize the output. See [ODB API website] [1] for more information about this technology.

[**Telecom SudParis**] [corp] - Copyright (C) 2014

Michel Vedrine - <mvedrine@gmail.com>

## General Usage Notes

### Installation
This is a web client written in pure JavaScript, there is no installation required, any modern browser will suffice.

### Execution
Currently, ODB API doesn't support CORS specifications. So in order to work with this client using AJAX, you must use [Google Chrome] [3] (or Chromium) with the flag "--disable-web-security"

```sh
chromium-browser --disable-web-security
```
This is due to the [RESTlet] [4] framework (server side) which doesn't support CORS. It is planned in the next release, so this situation is temporary. You can see their [detailled roadmap] [5] for more informations about RESTlet next release.

## Additionnal Libraries
ODB API Client uses an open source project to work properly :
- [DHTMLX] [2] (already included in the project)

## License
GPL v2


[1]:http://www-inf.int-evry.fr/~sellam_r/Tools/ODBAPI/index.html
[2]:http://dhtmlx.com/
[3]:http://www.google.com/intl/fr_fr/chrome/browser/
[4]:http://restlet.com/
[5]:https://github.com/restlet/restlet-framework-java/wiki/Road-map-of-version-2.3

[corp]:http://www.telecom-sudparis.eu/fr_accueil.html

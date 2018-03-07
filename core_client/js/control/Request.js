/* 
 * Copyright (C) 2014 Michel Vedrine <mvedrine@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

/**
 * Used to represent any type of http request.
 *
 * @class
 * @param {string}
 *            type Request type ("GET", "POST", ...)
 * @param {string}
 *            url The full url of the server. For a POST request type, don't
 *            include variables here.
 * @param {array}
 *            header An array of string representing the header ("Content-Type:
 *            application/json", ...).
 * @param {string}
 *            variables The list of variables when composing a POST request.
 * @param {function}
 *            callback The function to be executed when the response from the
 *            server is received.
 * @returns {Request}
 */
function Request(type_p, url_p, header_p, variables_p, callback_p) {
    'use strict';

    /**
     * The type of the request ("GET", "POST", ...).
     *
     * @private
     * @property {string}
     */
    var type = type_p,

        /**
         * Url of the server.
         *
         * @private
         * @property {string}
         */
        url = url_p,

        /**
         * Headers of the request.
         *
         * @private
         * @property {Array}
         */
        header = header_p,

        /**
         * Variables to be passed by the request.
         *
         * @private
         * @property {string}
         */
        variables = variables_p,

        /**
         * The function to be called when the result of the request comes.
         *
         * @private
         * @property {function}
         */
        callback = callback_p;

    /**
     * Instanciate a XmlHttpRequest object depending on the browser.
     *
     * @function
     * @private
     * @returns {undefined|XMLHttpRequest|ActiveXObject}
     */
    function getXMLHttpRequest() {
        var xhr = null;

        if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            try {
                xhr = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                xhr = new ActiveXObject("Microsoft.XMLHTTP");
            }
        } else {
            alert("Your browser doesn't support XMLHTTPRequest...");
            return;
        }
        return xhr;
    }

    /**
     * Send the request stored by the object.
     *
     * @function
     * @returns {undefined}
     */
    this.sendRequest = function () {
        var xhr = getXMLHttpRequest(), i, r;

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && (xhr.status === 200 || xhr.status === 0)) {
                if (callback) {
                    callback(xhr.responseText);
                    document.getElementById("loader").style.display = "none";
                }
            } else if (xhr.readyState < 4) {
                document.getElementById("loader").style.display = "block";
            }
        };

        xhr.open(type, url, true);

        if (header) {
            for (i = 0; i < header.length; i += 1) {
                r = header[i].split(": ");
                if (r.length !== 1) {
                    xhr.setRequestHeader(r[0], r[1]);
                }
            }
        }

        if (!variables) {
            variables = "";
        }

        xhr.send(variables);
    };
}
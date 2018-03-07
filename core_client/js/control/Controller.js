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
 * This class handles communications between a model and a view.
 * @class
 * @param {object} gui The GUI associated with this controller.
 * @returns {Controller}
 */
function Controller(gui_p) {
    'use strict';

    /** 
     * Name of the API to access.
     * @private
     * @constant
     * @default odbapi/
     */
    var API_NAME = "odbapi/",

        /** 
         * Gui associated with this controller.
         * @private
         * @property {Gui Object}
         */
        gui = gui_p,

        /** 
         * Server url.
         * @private
         * @property {string}
         */
        serverUrl = null;

    /**
     * Initiate a connection with a server.
     * @function
     * @param {type} url Url of the server.
     * @returns {undefined}
     */
    this.connect = function (url) {
        serverUrl = url;
    };

    /**
     * Disconnect from the server.
     * @function
     * @returns {undefined}
     */
    this.disconnect = function () {
        gui.disconnect(serverUrl);

        if (serverUrl !== null) {
            serverUrl = null;
        }
    };

    /**
     * Get all entities from an entity set.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @returns {undefined}
     */
    this.getAllE = function (dbType, dbName, esName) {
        new Request(
            "GET",
            serverUrl + API_NAME + "entityset/" + esName + "/entity", [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json"
            ],
            "",
            fillGrid
        ).sendRequest();
    };

    /**
     * Get an entity from an entity set.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @param {int} eId Entity ID (10, 1, 4000, ...).
     * @returns {undefined}
     */
    this.getE = function (dbType, dbName, esName, eId) {
        new Request(
            "GET",
            serverUrl + API_NAME + "entityset/" + esName + "/entity/" + eId, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json"
            ],
            "",
            fillEditor
        ).sendRequest();
    };

    /**
     * Create an entity for the designated database/entityset.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @param {int} eId Entity ID (10, 1, 4000, ...).
     * @param {type} params A JSON structure containing the data to be created.
     * @returns {undefined}
     */
    this.createE = function (dbType, dbName, esName, eId, params) {
        new Request(
            "POST",
            serverUrl + API_NAME + "entityset/" + esName + "/entity/" + eId, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName,
                "Accept: application/json",
                "Content-Type: application/json"
            ],
            params,
            gui.reloadGrid
        ).sendRequest();
    };

    /**
     * Update an entity contained in the designated database/entityset.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @param {int} eId Entity ID (10, 1, 4000, ...).
     * @param {type} params A JSON structure containing the data to be updated.
     * @returns {undefined}
     */
    this.updateE = function (dbType, dbName, esName, eId, params) {
        new Request(
            "PUT",
            serverUrl + API_NAME + "entityset/" + esName + "/entity/" + eId, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json",
                "Content-Type: application/json"
            ],
            params,
            gui.reloadGrid
        ).sendRequest();
    };

    /**
     * Delete an entity contained in the designated database/entityset.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @param {int} eId Entity ID (10, 1, 4000, ...).
     * @param {type} params (Optional)
     * @returns {undefined}
     */
    this.deleteE = function (dbType, dbName, esName, eId, params) {
        new Request(
            "DELETE",
            serverUrl + API_NAME + "entityset/" + esName + "/entity/" + eId, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json",
                "Content-Type: application/json"
            ],
            params,
            gui.reloadGrid
        ).sendRequest();
    };

    /**
     * Get all entity sets from a database.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @returns {undefined}
     */
    this.getAllES = function (dbType, dbName) {
        new Request(
            "GET",
            serverUrl + API_NAME + "entityset", [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json"
            ],
            "",
            fillTree
        ).sendRequest();
    };

    /**
     * Get an entity set from a database.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @returns {undefined}
     */
    this.getES = function (dbType, dbName, esName) {
        new Request(
            "GET",
            serverUrl + API_NAME + "entityset/" + esName, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json"
            ],
            "",
            null
        ).sendRequest();
    };

    /**
     * Create an entity set for a database.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @returns {undefined}
     */
    this.createES = function (dbType, dbName, esName) {
        new Request(
            "PUT",
            serverUrl + API_NAME + "entityset/" + esName, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json",
                "Content-Type: application/json"
            ],
            "",
            null
        ).sendRequest();
    };

    /**
     * Delete an entity set from a database.
     * @function
     * @param {string} dbType Type of the database ("couchDB", "MySQL", ...).
     * @param {string} dbName Name of the database. ("world", ...).
     * @param {string} esName Name of the entity set ("person", "cat", ...).
     * @returns {undefined}
     */
    this.deleteES = function (dbType, dbName, esName) {
        new Request(
            "DELETE",
            serverUrl + API_NAME + "entityset/" + esName, [
                "Database-Type: database/" + dbType,
                "Database-Name: " + dbName, "Accept: application/json",
                "Content-Type: application/json"
            ],
            "",
            null
        ).sendRequest();
    };

    /**
     * Get access rights.
     * @function
     * @returns {undefined}
     */
    this.getAccessRights = function () {
        new Request("GET", serverUrl + API_NAME + "accessright", [], "", alert).sendRequest();
    };

    /**
     * Get the environnement metadata.
     * @function
     * @returns {undefined}
     */
    this.getEnvMetaData = function () {
        new Request("GET", serverUrl + API_NAME + "metadata", [], "", alert).sendRequest();
    };

    /**
     * Get a database metadata.
     * @function
     * @param {string} dbName Name of the database.
     * @returns {undefined}
     */
    this.getDBMetaData = function (dbName) {
        new Request("GET", serverUrl + API_NAME + "database/" + dbName + "/metadata", [], "", alert).sendRequest();
    };

    /**
     * Get an entity set metadata.
     * @function
     * @param {string} dbName Name of the database.
     * @param {string} esName Name of the entity set.
     * @returns {undefined}
     */
    this.getESMetaData = function (dbName, esName) {
        new Request("GET", serverUrl + API_NAME + "entityset/" + esName + "/metadata", [], "", alert).sendRequest();
    };

    /**
     * Callback function for the getAllES response.
     * Fill the tree with database and entity set hierarchy.
     * @function
     * @private
     * @param {JSON string} json The response from the REST web service.
     * @returns {undefined}
     */
    function fillTree(json) {
        var response = JSON.parse(json),
            data = response.data;

        if (data) {
            var tree = gui.getTree(),
                focus = tree.getSelectedItemId(),
                i;

            tree.deleteChildItems(focus);

            for (i = 0; i < data.nameES.length; i += 1) {
                if (data.nameES[i] !== "_users" && data.nameES[i] !== "_replicator" && data.nameES[i] !== "system.indexes") {
                    tree.insertNewChild(focus, "es" + i, data.nameES[i], 0, 0, 0, 0);
                }
            }
        }
    }

    /**
     * Callback function for the getAllE response.
     * Fill the grid with data from an entityset.
     * @function
     * @private
     * @param {string} json The data in a JSON format.
     * @returns {undefined}
     */
    function fillGrid(json) {
        var response = JSON.parse(json);

        if (response) {
            var grid = gui.getGrid();
            grid.destructor();
            gui.initGrid();

            grid = gui.getGrid();
            grid.clearAll(true);

            var data = null;

            // add rows
            if (response["Database-Type"] === "database/MySQL") {
                data = mySqlFillGrid(grid, response.data);
                gui.getLayout().cells('c').collapse();
            } else if (response["Database-Type"] === "database/couchDB") {
                data = couchDbFillGrid(grid, response.data);
                gui.getLayout().cells('c').expand();
            } else if (response["Database-Type"] === "database/mongoDB") {
                data = mongoDbFillGrid(grid, response.data);
                gui.getLayout().cells('c').expand();
            }

            // fill the grid
            if (data !== null) {
                grid.parse(data, "json");
                grid.sortRows(0);
            }
        }
    }

    function mySqlFillGrid(grid, data) {
        var parser = new Parser(),
            i,
            result = {
                rows: []
            };

        grid.clearAll(true);
        grid.setHeader(parser.parseMySqlGridHeaders(data));
        grid.setColTypes(parser.parseMySqlGridColTypes(data));
        grid.setColSorting(parser.parseMySqlGridColSorting(data));
        grid.enableSmartRendering(true);
        grid.init();

        for (i = 0; i < data.length; i += 1) {
            result.rows[i] = {
                id: i,
                data: parser.parseMySqlGridRowData(data[i])
            };
        }

        result.rows.push({
            id: "new",
            data: parser.parseMySqlGridNewEntity(data[0])
        });

        return result;
    }

    function couchDbFillGrid(grid, data) {
        var i,
            result = {
                rows: []
            };

        grid.setHeader("id,rev");
        grid.setColTypes("ro,ro");
        grid.setColSorting("int,int");
        grid.enableSmartRendering(true);
        grid.init();

        for (i = 0; i < data.rows.length; i += 1) {
            result.rows[i] = {
                id: i,
                data: [data.rows[i].id, data.rows[i].value.rev]
            };
        }

        result.rows.push({
            id: "new",
            data: ["New Entity", ""]
        });

        return result;
    }

    function mongoDbFillGrid(grid, data) {
        var i,
            result = {
                rows: []
            };

        grid.setHeader("id");
        grid.setColTypes("ro");
        grid.setColSorting("int");
        grid.enableSmartRendering(true);
        grid.init();

        for (i = 0; i < data.length; i += 1) {
            result.rows[i] = {
                id: i,
                data: [data[i]._id]
            };
        }

        result.rows.push({
            id: "new",
            data: ["New Entity"]
        });

        return result;
    }

    /**
     * Callback function for the getE response.
     * Fill the editor with data from an entity.
     * @function
     * @private
     * @param {string} json The data in a JSON format.
     * @returns {undefined}
     */
    function fillEditor(json) {
        var editor = gui.getEditor();
        editor.setContent("");

        var response = JSON.parse(json);
        var data = response.data;

        if (response["Database-Type"] !== "database/MySQL") {
            var string = JSON.stringify(data, null, 2);

            // some pretty printing is needed to be readable
            string = string.replace(/({)/g, "$1<p><blockquote>")
                .replace(/(})/g, "</blockquote></p>$1")
                .replace(/(\[)/g, "$1<p><blockquote>")
                .replace(/(])/g, "</blockquote></p>$1")
                .replace(/(,)/g, "$1<p>");

            editor.setContent(string);
        }
    }
}
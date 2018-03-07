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
 * This object represents, and is in charge of constructing the GUI with the
 * DHTMLX framework.
 *
 * @class
 * @returns {Gui}
 */
function Gui() {
    'use strict';

    /**
     * Keyword used to detect database in the tree.
     *
     * @private
     * @constant
     */
    var DB_KEYWORD = "db",

        /**
         * Keyword used to detect entity sets in the tree.
         *
         * @private
         * @constant
         */
        ES_KEYWORD = "es",

        // ----------
        // Private properties
        // ----------

        /**
         * An object composing the window layout.
         *
         * @private
         * @property {DHTMLX Object}
         */
        layout = null,

        /**
         * A tree with nodes. Display databases and entity sets.
         *
         * @private
         * @property {DHTMLX Object}
         */
        tree = null,

        /**
         * A grid displaying entities from an entity set.
         *
         * @private
         * @property {DHTMLX Object}
         */
        grid = null,

        /**
         * An editor displaying details of an entity with editing capacity.
         *
         * @private
         * @property {DHTMLX Object}
         */
        editor = null,

        /**
         * A toolbar on top of the layout.
         *
         * @private
         * @property {DHTMLX Object}
         */
        toolbar = null,

        /**
         * A toolbar on top of the tree.
         *
         * @private
         * @property {DHTMLX Object}
         */
        esToolbar = null,

        /**
         * A toolbar on top of the grid.
         *
         * @private
         * @property {DHTMLX Object}
         */
        eToolbar = null,

        /**
         * The controller handling this GUI events.
         *
         * @private
         * @property {Controller Object}
         */
        controller = null,

        /**
         * An object creating external windows when needed.
         *
         * @private
         * @property {DHTMLX Object}
         */
        windows = null,

        /**
         * True if connected, false if not.
         *
         * @private
         * @property {boolean}
         */
        isConnected = null;
    
    this.initGrid = function () {
        grid = layout.cells("b").attachGrid();
        grid.setImagePath("./imgs/dhxgrid_skyblue/");
        grid.attachEvent("onRowSelect", fireEventRowSelected);
    }
    
    /**
     * Initialize all the components.
     *
     * @function
     * @returns {undefined}
     */
    this.init = function () {
        controller = new Controller(this);

        // layout
        layout = new dhtmlXLayoutObject(document.body, "3W");
        layout.cells("a").setWidth(300);
        layout.cells("a").setText("Database Explorer");
        layout.cells("b").setWidth(500);
        layout.cells("b").setText("EntitySet Explorer");
        layout.cells("c").setText("Entity Viewer");

        // tree
        tree = layout.cells("a").attachTree();
        tree.setImagePath("./imgs/dhxtree_skyblue/");
        tree.attachEvent("onSelect", fireEventTreeSelected);

        // grid
        this.initGrid();

        // editor
        editor = layout.cells("c").attachEditor();

        // toolbars
        toolbar = layout.attachToolbar();
        toolbar.attachEvent("onClick", fireEventMainToolbar);
        esToolbar = layout.cells("a").attachToolbar();
        esToolbar.attachEvent("onClick", fireEventTreeToolbar);
        eToolbar = layout.cells("b").attachToolbar();
        eToolbar.attachEvent("onClick", fireEventGridToolbar);

        if (toolbar) {
            toolbar.loadStruct([
                {
                    id: "text",
                    type: "text",
                    text: "ODB API Client"
                }, {
                    id: "sep0",
                    type: "separator"
                }, {
                    id: "connect",
                    type: "button",
                    text: "Connect To Server"
                }, {
                    id: "disconnect",
                    type: "button",
                    text: "Disconnect",
                    enabled: "false"
                }, {
                    id: "sep1",
                    type: "separator"
                }, {
                    id: "addDB",
                    type: "button",
                    text: "Add Database",
                    enabled: "false"
                }, {
                    id: "removeDB",
                    type: "button",
                    text: "Remove Database",
                    enabled: "false"
                }, {
                    id: "sep2",
                    type: "separator"
                }, {
                    id: "accessRights",
                    type: "button",
                    text: "Access Rights",
                    enabled: "false"
                }, {
                    id: "envMetaData",
                    type: "button",
                    text: "Env MetaData",
                    enabled: "false"
                }
            ]);
        }

        if (esToolbar) {
            esToolbar.loadStruct([
                {
                    id: "createES",
                    type: "button",
                    text: "Create ES",
                    enabled: "false"
                }, {
                    id: "deleteES",
                    type: "button",
                    text: "Delete ES",
                    enabled: "false"
                }, {
                    id: "getDBMetaData",
                    type: "button",
                    text: "DB MetaData",
                    enabled: "false"
                }, {
                    id: "getESMetaData",
                    type: "button",
                    text: "ES MetaData",
                    enabled: "false"
                }
            ]);
        }

        if (eToolbar) {
            eToolbar.loadStruct([
                {
                    id: "createE",
                    type: "button",
                    text: "Create Entity",
                    enabled: "false"
                }, {
                    id: "updateE",
                    type: "button",
                    text: "Update Entity",
                    enabled: "false"
                }, {
                    id: "deleteE",
                    type: "button",
                    text: "Delete Entity",
                    enabled: "false"
                }
            ]);
        }

        windows = new dhtmlXWindows();

        layout.cells('c').collapse();
        isConnected = false;
    };

    /**
     *
     * @function
     * @param {JSON}
     *            json Reload the source tree with databases and entitysets.
     * @returns {undefined}
     */
    this.loadTreeData = function (json) {
        tree.deleteChildItems(0);
        tree.loadJSONObject(json);
    };

    /**
     * Properly "disconnect" the current session. Disable some buttons, close
     * the source tree.
     *
     * @function
     * @param {string}
     *            url
     * @returns {undefined}
     */
    this.disconnect = function (url) {
        if (isConnected) {
            alert("You have been disconnected from " + url);

            toolbar.forEachItem(function (id) {
                toolbar.disableItem(id);
            });
            toolbar.enableItem("connect");

            esToolbar.forEachItem(function (id) {
                esToolbar.disableItem(id);
            });

            eToolbar.forEachItem(function (id) {
                eToolbar.disableItem(id);
            });

            tree.deleteChildItems(0);
            grid.clearAll();
            editor.setContent("");

            isConnected = false;
        }
    };

    /**
     * Returns the layout object.
     *
     * @function
     * @returns {Gui.layout|dhtmlXLayoutObject}
     */
    this.getLayout = function () {
        return layout;
    };

    /**
     * Returns the tree object.
     *
     * @function
     * @returns {Gui.tree}
     */
    this.getTree = function () {
        return tree;
    };

    /**
     * Returns the grid object.
     *
     * @function
     * @returns {Gui.grid}
     */
    this.getGrid = function () {
        return grid;
    };

    /**
     * Returns the editor object.
     *
     * @function
     * @returns {Gui.editor}
     */
    this.getEditor = function () {
        return editor;
    };

    this.reloadGrid = function () {
        fireEventTreeSelected(tree.getSelectedItemId());
    };

    /**
     * Handles events from the main toolbar.
     *
     * @function
     * @private
     * @param {type}
     *            id ID of the item triggered.
     * @returns {undefined}
     */
    function fireEventMainToolbar(id) {
        // depending on the id, requests are made
        if (id === "connect") {
            connect();
        } else if (id === "disconnect") {
            controller.disconnect();
        } else if (id === "addDB") {
            addDB();
        } else if (id === "removeDB") {
            removeDB();
        } else if (id === "accessRights") {
            controller.getAccessRights();
        } else if (id === "envMetaData") {
            controller.getEnvMetaData();
        }
    }

    /**
     * Handles events from the tree toolbar.
     *
     * @function
     * @private
     * @param {type}
     *            id ID of the item triggered.
     * @returns {undefined}
     */
    function fireEventTreeToolbar(id) {
        // depending on the id, requests are made
        if (id === "createES") {
            createES();
        } else if (id === "deleteES") {
            var dbName = tree.getParentId(tree.getSelectedItemId()),
                dbType = tree.getParentId(dbName);

            controller.deleteES(
                tree.getItemText(dbType),
                tree.getItemText(dbName),
                tree.getSelectedItemText()
            );
            tree.selectItem(dbName);
        } else if (id === "getDBMetaData") {
            controller.getDBMetaData(
                tree.getUserData(tree.getSelectedItemId(), "Database-Name")
            );
        } else if (id === "getESMetaData") {
            controller.getESMetaData(
                tree.getSelectedItemText(),
                tree.getUserData(tree.getSelectedItemId(), "Database-Name")
            );
        }
    }

    /**
     * Handles events from the grid toolbar.
     *
     * @function
     * @private
     * @param {type}
     *            id ID of the item triggered.
     * @returns {undefined}
     */
    function fireEventGridToolbar(id) {
        var dbName = tree.getParentId(tree.getSelectedItemId()),
            dbType = tree.getParentId(dbName);

        // depending on the id, requests are made
        if (id === "createE") {
            createE();
        } else if (id === "updateE") {
            controller.updateE(
                tree.getItemText(dbType),
                tree.getItemText(dbName), tree.getSelectedItemText(),
                grid.cellById(grid.getSelectedRowId(), 0).getValue(),
                editor.getContent().replace(/(<.[^(><.)]+>)/g, "").replace(/&nbsp;|<p>|<\/p>/g, "")
            );
        } else if (id === "deleteE") {
            controller.deleteE(
                tree.getItemText(dbType),
                tree.getItemText(dbName),
                tree.getSelectedItemText(),
                grid.cellById(grid.getSelectedRowId(), 0).getValue(),
                ""
            );
        }
    }

    /**
     * handles events from the grid selection.
     *
     * @function
     * @private
     * @param {string}
     *            id Id of the item triggered.
     * @returns {undefined}
     */
    function fireEventRowSelected(id) {
        if (id !== "new") {
            // if the "New entity" row has not been selected

            // get the entity selected...
            var dbName = tree.getParentId(tree.getSelectedItemId()),
                dbType = tree.getParentId(dbName);

            if (dbType !== "MySQL") {
                controller.getE(
                    tree.getItemText(dbType),
                    tree.getItemText(dbName),
                    tree.getSelectedItemText(),
                    grid.cellById(grid.getSelectedRowId(), 0).getValue()
                );

                // ...and activate some buttons
                eToolbar.forEachItem(function (id) {
                    eToolbar.disableItem(id);
                });
                eToolbar.enableItem("updateE");
                eToolbar.enableItem("deleteE");
            }
        } else {
            // if it has been selected

            // activate buttons related to an entity creation
            editor.setContent("Type your entity here.");
            eToolbar.forEachItem(function (id) {
                eToolbar.disableItem(id);
            });
            eToolbar.enableItem("createE");
        }
    }

    /**
     * Handles events from the tree selection.
     *
     * @function
     * @private
     * @param {string}
     *            id ID of the item triggered.
     * @returns {undefined}
     */
    function fireEventTreeSelected(id) {
        if (id.substring(0, DB_KEYWORD.length) === DB_KEYWORD) {
            // if a database is selected

            toolbar.enableItem("removeDB");

            esToolbar.forEachItem(function (id) {
                esToolbar.disableItem(id);
            });
            esToolbar.enableItem("createES");
            esToolbar.enableItem("getDBMetaData");

            eToolbar.forEachItem(function (id) {
                eToolbar.disableItem(id);
            });

            grid.clearAll();
            editor.setContent("");

            // get all entity sets to display into the tree
            var dbName = tree.getSelectedItemId(),
                dbType = tree.getParentId(dbName);

            controller.getAllES(
                tree.getItemText(dbType),
                tree.getItemText(dbName)
            );

        } else if (id.substring(0, ES_KEYWORD.length) === ES_KEYWORD) {
            // if an entityset is selected

            toolbar.disableItem("removeDB");

            esToolbar.forEachItem(function (id) {
                esToolbar.disableItem(id);
            });
            esToolbar.enableItem("deleteES");
            esToolbar.enableItem("getESMetaData");

            eToolbar.forEachItem(function (id) {
                eToolbar.disableItem(id);
            });

            grid.clearAll();

            // get all entities to display into the grid
            var dbName = tree.getParentId(tree.getSelectedItemId()),
                dbType = tree.getParentId(dbName);

            controller.getAllE(
                tree.getItemText(dbType),
                tree.getItemText(dbName),
                tree.getSelectedItemText()
            );
        } else {
            toolbar.enableItem("removeDB");

            esToolbar.forEachItem(function (id) {
                esToolbar.disableItem(id);
            });

            eToolbar.forEachItem(function (id) {
                eToolbar.disableItem(id);
            });

            grid.clearAll();
            editor.setContent("");
        }
    }

    /**
     * Create a popup windows asking for the server address.
     *
     * @function
     * @private
     * @returns {undefined}
     */
    function connect() {
        // create a windo...
        var window = windows.createWindow("window", 400, 200, 300, 150);
        window.denyResize();
        window.setText("Connect to Server");

        // ...with a form
        var form = window.attachForm([
            {
                type: "settings",
                position: "label-left",
                labelWidth: 100,
                inputWidth: 120
            }, {
                type: "block",
                inputWidth: "auto",
                offsetTop: 12,
                list: [
                    {
                        name: "field",
                        type: "input",
                        label: "Address",
                        value: "http://localhost:8182/"
                    }, {
                        name: "proceed",
                        type: "button",
                        value: "Proceed",
                        offsetLeft: 80,
                        offsetTop: 14
                    }
                ]
            }
        ], true);

        form.attachEvent("onButtonClick", function () {
            // pass the server url to the controller
            controller.connect(form.getItemValue("field"));

            window.close();
            isConnected = true;

            // activate some buttons
            toolbar.forEachItem(function (id) {
                toolbar.enableItem(id);
            });
            toolbar.disableItem("connect");
            toolbar.disableItem("removeDB");
        });
    }

    /**
     * Create a popup window asking for a databse type and name.
     *
     * @function
     * @private
     * @returns {undefined}
     */
    function addDB() {
        // create a window...
        var window = windows.createWindow("window", 400, 200, 300, 200);
        window.denyResize();
        window.setText("Add Database");

        // ...with a form
        var form = window.attachForm([
            {
                type: "settings",
                position: "label-left",
                labelWidth: 100,
                inputWidth: 120
            }, {
                type: "block",
                inputWidth: "auto",
                offsetTop: 12,
                list: [
                    {
                        name: "dbType",
                        type: "input",
                        label: "DB Type",
                        value: "couchDB"
                    }, {
                        name: "dbName",
                        type: "input",
                        label: "DB Name",
                        value: "world",
                        disabled: "true"
                    }, {
                        name: "proceed",
                        type: "button",
                        value: "Proceed",
                        offsetLeft: 80,
                        offsetTop: 14
                    }
                ]
            }
        ], true);

        form.attachEvent("onChange", function () {
            if (form.getItemValue("dbType") !== "couchDB") {
                form.enableItem("dbName");
            } else {
                form.disableItem("dbName");
            }
        });

        form.attachEvent("onButtonClick", function () {
            var dbType = form.getItemValue("dbType"),
                dbName = form.getItemValue("dbName");

            if (tree.getItemText(dbType) === 0) {
                tree.insertNewChild(0, dbType, dbType, 0, 0, 0, 0);
            }

            if (dbType === "couchDB") {
                if (tree.getItemText(DB_KEYWORD + ".") === 0) {
                    tree.insertNewChild(dbType, DB_KEYWORD + ".", ".", 0, 0, 0, 0);
                }
            } else {
                if (tree.getItemText(DB_KEYWORD + dbName) === 0) {
                    tree.insertNewChild(dbType, DB_KEYWORD + dbName, dbName, 0, 0, 0, 0);
                }
            }
            window.close();
        });
    }

    /**
     * Remove a database from the GUI only, not from the databases.
     *
     * @function
     * @private
     * @returns {undefined}
     */
    function removeDB() {
        tree.deleteItem(tree.getSelectedItemId(), true);
        toolbar.disableItem("removeDB");
        grid.clearAll();
        editor.setContent("");
    }

    /**
     * Create a windows where the user has to type the name of the entity set he
     * wants to create. The entity set is created in the selected database.
     *
     * @function
     * @private
     * @returns {undefined}
     */
    function createES() {
        // create a window...
        var window = windows.createWindow("window", 400, 200, 300, 150);
        window.denyResize();
        window.setText("Create Entity Set");

        // ...and put a form into it
        var form = window.attachForm([
            {
                type: "settings",
                position: "label-left",
                labelWidth: 100,
                inputWidth: 120
            }, {
                type: "block",
                inputWidth: "auto",
                offsetTop: 12,
                list: [
                    {
                        name: "field",
                        type: "input",
                        label: "ES Name",
                        value: "cat"
                    }, {
                        name: "proceed",
                        type: "button",
                        value: "Proceed",
                        offsetLeft: 80,
                        offsetTop: 14
                    }
                ]
            }
        ], true);

        form.attachEvent("onButtonClick", function () {
            var dbName = tree.getSelectedItemId(),
                dbType = tree.getParentId(dbName);

            controller.createES(
                tree.getItemText(dbType),
                tree.getItemText(dbName),
                form.getItemValue("field")
            );

            window.close();
            fireEventTreeSelected(tree.getSelectedItemId());
        });
    }

    /**
     * Create a popup window asking for the entity ID and initiate a request
     * with the correct parameters.
     *
     * @function
     * @private
     * @returns {undefined}
     */
    function createE() {
        // create a window...
        var window = windows.createWindow("window", 400, 200, 300, 150);
        window.denyResize();
        window.setText("Create Entity");

        // ...and put a form into it
        var form = window.attachForm([
            {
                type: "settings",
                position: "label-left",
                labelWidth: 100,
                inputWidth: 120
            }, {
                type: "block",
                inputWidth: "auto",
                offsetTop: 12,
                list: [
                    {
                        name: "field",
                        type: "input",
                        label: "Entity ID",
                        value: "1"
                    }, {
                        name: "proceed",
                        type: "button",
                        value: "Proceed",
                        offsetLeft: 80,
                        offsetTop: 14
                    }
                ]
            }
        ], true);

        form.attachEvent("onButtonClick", function () {
            var editorContent,
                dbName = tree.getParentId(tree.getSelectedItemId()),
                dbType = tree.getParentId(dbName);

            // check for a valid JSON
            try {
                editorContent = JSON.stringify(
                    JSON.parse(
                        editor.getContent()
                        .replace(/(<.[^(><.)]+>)/g, "")
                        .replace(/&nbsp;|<p>|<\/p>/g, "")
                    )
                );
            } catch (e) {
                alert('Invalid JSON');
                return;
            }

            controller.createE(
                tree.getItemText(dbType),
                tree.getItemText(dbName),
                tree.getSelectedItemText(),
                form.getItemValue("field"),
                editorContent
            );

            window.close();
        });
    }
}
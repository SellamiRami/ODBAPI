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

function Parser() {
    'use strict';

    this.parseMySqlGridHeaders = function (data) {

        var result = "",
            key;

        for (key in data[0]) {
            if (data[0].hasOwnProperty(key)) {
                result += key + ',';
            }
        }

        return result.slice(0, -1);
    };

    this.parseMySqlGridColTypes = function (data) {

        var result = "",
            key;

        for (key in data[0]) {
            if (data[0].hasOwnProperty(key)) {
                result += "ro,";
            }
        }

        return result.slice(0, -1);
    };

    this.parseMySqlGridColSorting = function (data) {

        var result = "",
            key;

        for (key in data[0]) {
            if (data[0].hasOwnProperty(key)) {
                result += "str,";
            }
        }

        return result.slice(0, -1);
    };

    this.parseMySqlGridRowData = function (data) {

        var result = [],
            key;

        for (key in data) {
            if (data.hasOwnProperty(key)) {
                result.push(data[key]);
            }
        }

        return result;
    };

    this.parseMySqlGridNewEntity = function (data) {

        var result = [],
            key;

        for (key in data) {
            if (data.hasOwnProperty(key)) {
                result.push(null);
            }
        }
        result[0] = "New Entity";

        return result;
    };
}
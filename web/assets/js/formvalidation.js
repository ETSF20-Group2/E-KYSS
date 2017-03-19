var LETTERS = ["d_", "i_", "f_", "r_"];
var CODES = ["11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "23", "30",
    "41", "42", "43", "44", "100"];
/**
 * Sum the row, with activity code act_code, and place value inside the sum column.
 * @param act_code
 */
function validateRow(act_code) {
    var total = 't_';
    var TextFieldArray = Array.from(LETTERS, (l) => l+act_code); // construct tag-id ‘11d’ t.ex.
    var res = TextFieldArray.reduce(((acc, cur) => acc + parseCell(cur, function(name) {
            return document.getElementsByName(name)[0];
        })), 0);
    document.getElementsByName(total + act_code)[0].value = res;
}

/**
 * Target column to sum. if target column is "sum" (i.e. total sum), sum the sum columns
 * and save in DOM element with name=sum. Important to note; the DOM element name sum must be unique
 * or lest we enter inside the dark, scary terrain of the monsters we call undefined.
 * @param col
 */
function calculateSum(col) {
    var TextFieldArray = Array.from(CODES, (l) => col+l);
    var res = TextFieldArray.reduce(((accumulator, current) => accumulator + parseCell(current, function(name) {
            return document.getElementsByName(name)[0];
        })), 0);
    document.getElementsByName("sum")[0].value = res;
}

function validateCol(col) {
    var TextFieldArray = Array.from(CODES, (code) => {
        if(parseInt(code) < 21) return col+"_"+code;
    });
    var res = TextFieldArray.reduce(((accumulator, current) => accumulator + parseCell(current, function(name) {
            return document.getElementsByName(name)[0];
        })), 0);
    document.getElementsByName("t_" + col)[0].value = res;
}
/**
 * Check, if valid time input, and update time report accordingly. The DOM element that triggers this function
 * passes "this" as parameter. Validates input in cell, then calculates row && column accordlingly.
 * @param ref
 */
function checkValidTI(ref) {
    var code = ref.name.substring(2,ref.name.length);
    var tag = ref.name.charAt(0);
    if(parseInt(code) < 21) {
        validateRow(code); // validate && calculate row
        validateCol(tag);   // validate && calculate col
    }
    calculateSum('t_'); // sum all fields with name beginning  with t_
}



/**
 * Parses a "cell element", of type <input> in the timereport.jsp form, with callback function ifGetElem
 * and returns the value contained, if element exists. Returns 0 if parsing fails, or if the DOM element can not
 * be found or does not exist.
 * @param e
 * @param ifGetElem
 * @returns {*}
 */
function parseCell(e, ifGetElem) {
    var res;
    var elem;
    if((elem = ifGetElem(e)) != undefined)
        if ((res = parseInt(Math.abs(elem.value))) >= 0) {
            elem.style.color = "black";
            elem.style["box-shadow"] = "";
            elem.style["border"] = "";
            return res;
        }
        else {
            elem.style["color"] = "red";
            elem.style["box-shadow"] = "0 0 5px rgba(255, 0, 0, 1)";
            elem.style["border"] = "2px solid rgba(255, 2, 2, 1)";
        }
    return 0;
}
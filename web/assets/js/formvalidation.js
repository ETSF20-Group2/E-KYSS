var LETTERS = ["d", "i", "f", "r"];
var CODES = ["11", "12", "13", "14", "15", "16", "17", "18", "19", "21", "22", "23", "30",
    "41", "42", "43", "44", "100"];

/**
 * Sum the row, with activity code act_code, and place value inside the sum column.
 * @param act_code
 */
function validateRow(act_code) {
    var target = 's';
    var arr = Array.from(LETTERS, (l) => act_code+l); // construct tag-id ‘11d’ t.ex.
    var res = arr.reduce(((acc, cur) => acc + parseCell(cur, function(name) {
            return document.getElementsByName(name)[0];
        })), 0);
    console.log(res);
    document.getElementsByName(act_code+target)[0].value = res;
    console.log(res);
}

/**
 * Target column to sum. if target column is "sum" (i.e. total sum), sum the sum columns
 * and save in DOM element with name=sum. Important to note; the DOM element name sum must be unique
 * or lest we enter inside the dark, scary terrain of the monsters we call undefined.
 * @param col
 */
function validateCol(col) {
    var arr = Array.from(CODES, (l) => l+col);
    var res = arr.reduce(((acc, cur) => acc + parseCell(cur, function(name) {
            return document.getElementsByName(name)[0];
        })), 0);
    if(col!='s') document.getElementsByName(col+'s')[0].value = res;
    else if(col=='s') document.getElementsByName("sum")[0].value = res;
}

/**
 * Check, if valid time input, and update time report accordingly. The DOM element that triggers this function
 * passes "this" as parameter.
 * @param ref
 */
function checkValidTI(ref) {
    var code = ref.name.substring(0,ref.name.length-1);
    var tag = ref.name.charAt(code.length-1);
    console.log("code: " + code + " tag: " + tag);
    validateRow(code);
    validateCol(tag);
    validateCol('s')
}

/**
 * Parses a "cell element", of type <input> in the timereport.jsp form, with callback function ifGetElem
 * and returns the value contained, if element exists. Returns 0 if parsing fails, or if the DOM element can not
 * be found or does not exist.
 * return 0 if
 * @param e
 * @param ifGetElem
 * @returns {*}
 */
function parseCell(e, ifGetElem) {
    var res;
    var elem;
    if((elem = ifGetElem(e)) != null) {
        if((res = parseInt(elem.value)) >= 0){
            return res;
        }
    }
    return 0;
}

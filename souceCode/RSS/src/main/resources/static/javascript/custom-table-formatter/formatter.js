function _formatte_id(value, row, index) {
    if(index == 0){
        contactsNameMap = new Map();
    }

    contactsNameMap.set(value,row.name);

    return '<input name="table_id" type="checkbox" value="' + value + '" />';
}


function _formatte_time(value, row, index) {
    if(value==undefined){
        return '-'
    }

    return getFormattedTime(value);
}

function _formatte_select_enable(value, row, index) {
    return $.trim(value) ==$.trim('1') ? "是":"否" ;
}

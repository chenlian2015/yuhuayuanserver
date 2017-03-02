function layerPhotoOpen(selector, type) {
    var selectString = ".";
    if (type && type == "id") {
        selectString = "#";
    }
    selectString += selector;
    layer.photos({
        start: 1,
        closeBtn: true,
        photos: selectString
    });
}
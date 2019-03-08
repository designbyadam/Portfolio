var totalMoneyIn = 0;

$(document).ready(function () {

    loadItems();

});

function loadItems() {
    clearItemList()
    var itemsColumn = $('#itemsColumn');

    $.ajax ({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function (data, status) {
            $.each(data, function (data, item) {

                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var id = item.id;


                var button = '<button class="btn-sq-lg btn-default"><a onclick="selectItem(' + id + ')">';
                button += '<p align="left">' + id + '</p>';
                button += '<p>' + name + '</p>';
                button += '<p>$' + price + '</p>';
                button += '<p>Quantity Left: ' + quantity + '</p>';
                button += '</button>';
                itemsColumn.append(button);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#errorMessages')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function clearItemList() {
    $('#itemsColumn').empty();
}

function selectItem(id) {
    $('#itemBox').val(id);
}

// function showDvd(dvdId) {
//     $.ajax({
//         type: 'GET',
//         url: 'http://localhost:8080/dvds',
//         success: function(data, status) {
//             $('#')
//         },
//         error: function(jqXHR, textStatus, errorThrown) {
//             $('#errorMessages')
//                  .append($('<li>')
//                  .attr({class: 'list-group-item list-group-item-danger'})
//                  .text('Error calling web service. Please try again later.'));
//             }
//     })

// }
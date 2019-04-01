var totalMoneyIn = 0;

$(document).ready(function () {

    clearItemList();
    loadItems();
    clearItem();
    clearMessage();
    $('#totalChange').val('');
    $('#totalMoney').val('');


});

function loadItems() {
    clearItemList()
    var itemsColumn = $('#itemsColumn');

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        success: function (data, status) {
            $.each(data, function (index, item) {

                var name = item.name;
                var price = item.price;
                var quantity = item.quantity;
                var id = item.id;


                var button = '<button class="btn-sq-lg btn-light" onclick="selectItem(' + id + ');">';
                button += '<p align="left">' + id + '</p>';
                button += '<p>' + name + '</p>';
                button += '<p>$' + price.toFixed(2) + '</p>';
                button += '<p>Quantity Left: ' + quantity + '</p>';
                button += '</button>';
                itemsColumn.append(button);
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#message')
                .append($('<li>')
                    .attr({ class: 'list-group-item list-group-item-danger' })
                    .text('Error calling web service. Please try again later.'));
        }
    });
};

function clearItemList() {
    $('#itemsColumn').empty();
};

function clearItem() {
    $('#itemBox').val(' ');
};

function clearChange() {
    $('#totalChange').val(' ');
};

function clearTotalMoney() {
    $('#totalMoney').val(' ');
};

function selectItem(id) {
    $('#itemBox').val(id);
};

function clearMessage() {
    $('#message').val(' ');
};

$('#buttonPurchase').click(function () {
    clearMessage();
    var itemId = $('#itemBox').val();
    var money = $('#totalMoney').val();

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/money/' + money + '/item/' + itemId,
        dataType: 'json',
        success: function (data) {
            var changeString = '';
            var quarters = data.quarters;
            var dimes = data.dimes;
            var nickels = data.nickels;



            if (quarters == 1) {
                changeString += quarters + ' Quarter ';
            } else if (quarters > 1) {
                changeString += quarters + ' Quarters ';
            }

            if (dimes == 1) {
                changeString += dimes + ' Dime ';
            } else if (dimes > 1) {
                changeString += dimes + ' Dimes ';
            }

            if (nickels == 1) {
                changeString += nickels + ' Nickel ';
            } else if (nickels > 1) {
                changeString += nickels + ' Nickels ';
            }

            $('#message').val('Thank you!!!');
            $('#totalChange').val(changeString);
            totalMoneyIn = 0;
            clearTotalMoney();
            clearItemList();
            loadItems();


        },
        error: function (JQXHR, textStatus, errorThrown) {
            var jsonError = $.parseJSON(JQXHR.responseText);
            $('#message').val(jsonError.message);
        }

    })
})

$('#buttonDollar').click(function () {
    clearChange();
    totalMoneyIn += 100;
    $('#totalMoney').val((totalMoneyIn / 100).toFixed(2));
});

$('#buttonQuarter').click(function () {
    clearChange();
    totalMoneyIn += 25;
    $('#totalMoney').val((totalMoneyIn / 100).toFixed(2));
});

$('#buttonDime').click(function () {
    clearChange();
    totalMoneyIn += 10;
    $('#totalMoney').val((totalMoneyIn / 100).toFixed(2));
});

$('#buttonNickel').click(function () {
    clearChange();
    totalMoneyIn += 05;
    $('#totalMoney').val((totalMoneyIn / 100).toFixed(2));
});

$('#buttonChangeReturn').click(function () {
    if (totalMoneyIn > 0) {
        var totalMoneyInteger = parseInt(totalMoneyIn);
        var quarters = parseInt(totalMoneyInteger / 25);
        totalMoneyInteger = totalMoneyInteger - quarters * 25;
        var dimes = parseInt(totalMoneyInteger / 10);
        totalMoneyInteger = totalMoneyInteger - dimes * 10;
        var nickels = parseInt(totalMoneyInteger / 5);
        var changeString = '';
        if (quarters == 1) {
            changeString += quarters + ' Quarter ';
        } else if (quarters > 1) {
            changeString += quarters + ' Quarters ';
        }

        if (dimes == 1) {
            changeString += dimes + ' Dime ';
        } else if (dimes > 1) {
            changeString += dimes + ' Dimes ';
        }

        if (nickels == 1) {
            changeString += nickels + ' Nickel ';
        } else if (nickels > 1) {
            changeString += nickels + ' Nickels ';
        }

        $('#totalChange').val(changeString);
    } else {
        clearChange();
    }

    clearTotalMoney();
    clearItem();
 
    clearMessage();
    totalMoneyIn = 0;

});
$(document).ready(function () {
    cargarDatos();
    var dni = "";

    $(document).on('click', '.btnEdit', function () {
        fila = $(this).closest('tr');
        dni = fila.find("td:eq(0)").text();
        name = fila.find("td:eq(1)").text();
        lastName = fila.find("td:eq(2)").text();
        age = fila.find("td:eq(3)").text();

        $("#nameE").val(name);
        $("#lastNameE").val(lastName);
        $("#ageE").val(age);

        $("#modalCrudEditar").modal("show");
    });

    $(document).on('click', '.btnDelete', function () {
        fila = $(this).closest('tr');
        dni = fila.find("td:eq(0)").text();
        $("#modalCrudEliminar").modal("show");
    });

    /* $(document).on('submit', '#formAgregar', function (e) {
     e.preventDefault();
     dniAdd=$('#dniA').val();
     name= $('#nameA').val();
     lastname= $('#lastNameA').val();
     age= $('#ageA').val();  
     
     if(!validateDni(dniAdd)){
     alert("Sorry, We have problems with your dni, please enter one valid");
     }else{
     aler("Everything is ok");
     }
     
     });*/


    $('#formAgregar').submit(function (e) {
        e.preventDefault();
        dniAdd = $('#dniA').val();
        name = $('#nameA').val();
        lastName = $('#lastNameA').val();
        age = $('#ageA').val();


        if (!validateDni(dniAdd)) {
            alert("Tenemos problemas con tu cedula, por favor verificala");
        } else {
            if (!validateInputs(name, lastName, age)) {
                alert("Tenemos problemas con la información proporcionada, por favor revisa tus datos");
            } else {
                $.ajax({
                    url: "http://localhost:8080/ProyectoFinal/SVUsuarios",
                    type: "POST",
                    data: {
                        'cedula': dniAdd,
                        'nombre': name,
                        'apellido': lastName,
                        'edad': age
                    }, success: function (resultado) {
                        alert("Exito al enviar datos al servlet");
                        $("#modalCrudAgregar").modal("hide");
                        $("#table-div tbody").empty();
                        cargarDatos();
                    }
                });
            }

        }

    });

    //49:44


    $("#formEditar").submit(function (e) {
        e.preventDefault();
        name = $('#nameE').val();
        lastName = $('#lastNameE').val();
        age = $('#ageE').val();

        if (!validateInputs(name, lastName, age)) {
            alert("Tenemos problemas con la información proporcionada, por favor revisa tus datos");
        } else {
            $.ajax({
                url: "http://localhost:8080/ProyectoFinal/SVUsuarios",
                type: "PUT",
                data: {
                    'cedula': dni,
                    'nombre': name,
                    'apellido': lastName,
                    'edad': age
                }, success: function (resultado) {
                    alert("Exito al enviar datos al servlet");
                    $("#modalCrudEditar").modal("hide");
                    $("#table-div tbody").empty();
                    cargarDatos();
                }
            });
        }


    });

    $("#formEliminar").submit(function (e) {
        e.preventDefault();

        $.ajax({
            url: "http://localhost:8080/ProyectoFinal/SVUsuarios",
            type: "DELETE",
            data: {
                'cedula': dni
            }, success: function (resultado) {
                alert("Exito al enviar datos al servlet");
                $("#modalCrudEliminar").modal("hide");
                $("#table-div tbody").empty();
                cargarDatos();
            }
        });


    });





});




function cargarDatos() {
    $.get("http://localhost:8080/ServiciosProyectoFinal/api/getInfo", function (response) {
        //$.get("http://localhost:8080/ProyectoFinal/SVUsuarios", function (response) {
        // Actualizar la tabla o cualquier otro elemento en tu página
        // con los datos recibidos en la respuesta
        // Por ejemplo:
        let obj = response;

        for (let k in obj) {

            $("#tablaDatos").append(
                    '<tr><td class="mdl-data-table__cell--non-numeric">' + obj[k].dni + '</td>' +
                    '<td class="mdl-data-table__cell--non-numeric">' + obj[k].name + '</td>' +
                    '<td class="mdl-data-table__cell--non-numeric">' + obj[k].lastName + '</td>' +
                    '<td class="mdl-data-table__cell--non-numeric">' + obj[k].age + '</td>' +
                    '<td class="mdl-data-table__cell">\n\
                                <center>\n\
                                    <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect button--colored-teal btnEdit" id="editar">\n\
                                    <i class="material-icons">create</i>Editar</button> <button class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect button--colored-red btnDelete" id="eliminar">\n\
                                    <i class="material-icons">cancel</i>Eliminar</button>\n\
                                </center>'
                    );
        }
        //$('#tablaDatos').html(response);
    });
}

function validateDni(dni) {

    if (dni.length !== 10) {
        return false;
    }

    //verify if the first two digits are valids
    var province = parseInt(dni.substring(0, 2));
    if (province < 1 || province > 24) {
        return false;
    }

    //verify the last digit(verification digit)
    var verificationDigit = parseInt(dni.charAt(9));
    var sum = 0;

    for (var i = 0; i < 9; i++) {
        var digit = parseInt(dni.charAt(i));
        if (i % 2 === 0) {
            digit *= 2;
            if (digit > 9) {
                digit -= 9;
            }
        }
        sum += digit;
    }

    var module = sum % 10;
    var result = (module === 0) ? 0 : 10 - module;
    return result === verificationDigit;


}


function validateInputs(name, lastName, age) {

    var regex = /^[A-ZÁÉÍÓÚÑ][a-zA-ZáÁéÉíÍóÓúÚñÑ]+$/;
    if (regex.test(name) && regex.test(lastName) && (age > 0 && age < 100)) {
        return true;
    } else {
        return false;
    }
}



<%-- 
    Document   : index
    Created on : 13 jun 2023, 16:52:31
    Author     : ariel
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Controller.User"%>
<%@page import="Servlets.SVUsuarios"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
            // Invalidar la sesión actual
            String user = (String) request.getSession().getAttribute("userSession");
            if(user == null){
                response.sendRedirect("index.jsp");
            }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" type="image/png" href="images/DB_16х16.png">

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Index</title>

        <link href='https://fonts.googleapis.com/css?family=Roboto:400,500,300,100,700,900' rel='stylesheet'
              type='text/css'>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!-- inject:css -->

        <link rel="stylesheet" href="Html/css/application.min.css">
        <link rel="stylesheet" href="Html/css/style.css">
        <!-- endinject -->
        <script src="Html/js/jquery-1.11.1.min.js"></script>
        <script src="Html/js/jquery-1.11.1.js"></script>
        <script src="Html/js/JqueryLib.js"></script>
        <script src="Html/js/modal.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js" integrity="sha384-fbbOQedDUMZZ5KreZpsbe1LCZPVmfTnH7ois6mU1QK+m14rQ1l2bGBq41eYeM/fS" crossorigin="anonymous"></script>
        <script type="text/javascript" src="https://www.jeasyui.com/easyui/jquery.min.js"></script>
        <script type="text/javascript" src="https://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="logic.js"></script>

        <title>JSP Page</title>
    </head>
    <body>
     
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header is-small-screen">
            <header class="mdl-layout__header">
                <div class="mdl-layout__header-row">
                    <div class="mdl-layout-spacer"></div>

                    <!-- Cuenta-->      
                    <div class="avatar-dropdown" id="icon">
                        <span><%=user%></span>
                        <img src="Html/images/team.png">
                    </div>

                    <!-- Account dropdawn-->
                    <ul class="mdl-menu mdl-list mdl-menu--bottom-right mdl-js-menu mdl-js-ripple-effect mdl-shadow--2dp account-dropdown"
                        for="icon">
                        <li class="mdl-list__item mdl-list__item--two-line">
                            <span class="mdl-list__item-primary-content">
                                <span class="material-icons mdl-list__item-avatar"></span>
                                <span><%=user%></span>
                            </span>
                        </li>

                        <li class="list__item--border-top"></li>
                        <a href="logout.jsp">
                            <li class="mdl-menu__item mdl-list__item">
                                <span class="mdl-list__item-primary-content">
                                    <i class="material-icons mdl-list__item-icon text-color--secondary">exit_to_app</i>
                                    Cerrar Sesión
                                </span>
                            </li>
                        </a>
                    </ul>
                </div>
            </header>

            <div class="mdl-layout__drawer">
                <header>Aesir</header>



                <div class="scroll__wrapper" id="scroll__wrapper">

                    <div>
                        <img src="./Html/images/team.png" alt="alt"/>
                    </div>


                    <div class="scroller" id="scroller">
                        <div class="scroll__container" id="scroll__container">
                            <nav class="mdl-navigation">
                                <a class="mdl-navigation__link mdl-navigation__link--current" >
                                    <i class="material-icons" role="presentation">dashboard</i>
                                    Principal
                                </a>
                            </nav>
                        </div>
                    </div>
                    <div class='scroller__bar' id="scroller__bar"></div>
                </div>
            </div>

            <main class="mdl-layout__content ">
                <br>
                <div class="mdl-grid ui-tables">
                    <div class="mdl-cell mdl-cell--10-col-desktop mdl-cell--10-col-tablet mdl-cell--4-col-phone">
                        <div class="mdl-card mdl-shadow--1dp">
                            <div class="mdl-card__title">
                                <h1 class="mdl-card__title-text">Tabla de Datos</h1>
                                <button class="mdl-button button--colored-teal" id="agregar">
                                    <i class="material-icons">add_box</i>
                                    Agregar
                                </button> 
                            </div>
                            <div class="mdl-card__supporting-text no-padding" id="table-div">

                                <table class="mdl-data-table mdl-js-data-table">
                                    <thead>
                                        <tr>

                                            <th class="mdl-data-table__cell--non-numeric">Cedula</th>
                                            <th class="mdl-data-table__cell--non-numeric">Nombre</th>
                                            <th class="mdl-data-table__cell--non-numeric">Apellido</th>
                                            <th class="mdl-data-table__cell--non-numeric">Edad</th>
                                            <th class="mdl-data-table__select">Acciones</th>

                                        </tr>
                                    </thead>
                                    <tbody id="tablaDatos">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </main>
        </div>
        <!-- MODAL -->



        <div class="modal fade" id="modalCrudEliminar" tabindex="-1" role="dialog" aria-labelledby="modal-register-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modal-register-label">Eliminar Usuario</h3>
                        <p class="modal">Edite los datos del Usuario:</p>
                        <button type="button" class="btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div> 
                    <div class="modal-body">
                        <form role="form" action="" method="post" class="registration-form" id="formEliminar">

                            <div class="modal-body">     
                                <p class="text-white">¿Está seguro de que desea eliminar el Usuario?</p>
                                <p class="text-danger"><small>Esta acción no se puede deshacer.</small></p>
                            </div>

                            <div class="modal-footer">
                                <input type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close" value=" Cancelar ">
                                <input type="submit" class="btn btn-danger" value=" Eliminar Usuario ">
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalCrudEditar" tabindex="-1" role="dialog" aria-labelledby="modal-register-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modal-register-label">Editar Usuario</h3>
                        <p class="modal">Edite los datos del Usuario:</p>
                        <button type="button" class="btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div> 
                    <div class="modal-body">
                        <form role="form" action="" method="post" class="registration-form" id="formEditar">
                            <div class="form-group">
                                <label class="sr-only" for="nameE">Nombre: </label>
                                <input type="text" name="name" placeholder="Nombre..." class="form-first-name form-control" id="nameE" pattern="[A-Za-z]+" title="Solo puede ingresar letras">
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="sr-only" for="lastNameE">Apellido: </label>
                                <input type="text" name="lastName" placeholder="Apellido..." class="form-last-name form-control" id="lastNameE"  pattern="[A-Za-z]+"  title="Solo puede ingresar letras">
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="sr-only" for="ageE">Edad: </label>
                                <input type="text" name="age" placeholder="Edad..." class="form-email form-control" id="ageE"  pattern="[0-9]*"  title="Solo puede ingresar números">
                            </div>
                            <br>

                            <div class="modal-footer">
                                <input type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close" value=" Cancelar ">
                                <input type="submit" class="btn btn-success" value=" Editar Usuario ">
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalCrudAgregar" tabindex="-1" role="dialog" aria-labelledby="modal-register-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modal-register-label">Agregar Usuario</h3>
                        <p class="modal">Ingrese los datos del Usuario:</p>
                        <button type="button" class="btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div> 
                    <div class="modal-body">
                        <form role="form" action="" method="" class="registration-form" id="formAgregar">
                            <div class="form-group">
                                <label class="sr-only" for="dniA">Cedula: </label>
                                <input type="text" name="dniA" placeholder="Cedula..." class="form-first-name form-control" id="dniA" pattern="[0-9]*" title="Solo puede ingresar números">
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="sr-only" for="nameA">Nombre: </label>
                                <input type="text" name="nameA" placeholder="Nombre..." class="form-first-name form-control" id="nameA" pattern="[A-Za-z]+" title="Solo puede ingresar letras">
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="sr-only" for="lastNameA">Apellido: </label>
                                <input type="text" name="lastNameA" placeholder="Apellido..." class="form-last-name form-control" id="lastNameA" pattern="[A-Za-z]+" title="Solo puede ingresar letras">
                            </div>
                            <br>
                            <div class="form-group">
                                <label class="sr-only" for="ageA">Edad: </label>
                                <input type="text" name="ageA" placeholder="Edad..." class="form-email form-control" id="ageA" pattern="[0-9]*+" title="Solo puede ingresar números">
                            </div>
                            <br>

                            <div class="modal-footer">
                                <input type="button" class="btn btn-warning" data-bs-dismiss="modal" aria-label="Close" value=" Cancelar ">
                                <input type="submit" class="btn btn-success" value=" Agregar Usuario ">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- inject:js -->
        <script src="Html/js/getmdl-select.min.js"></script>
        <script src="Html/js/material.min.js"></script>
        <script src="Html/js/scroll/scroll.min.js"></script>
        <script src="Html/js/widgets/table/table.min.js"></script>

    </body>
</html>

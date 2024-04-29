<%-- 
    Document   : EcranPrevision
    Created on : 11 déc. 2023, 10:37:10
    Author     : Tohy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Prevision"%>
<%@page import="Model.DetailsPrevision"%>
<%@page import="java.util.List"%>
<% 
    Prevision prevision = (Prevision)request.getAttribute("prevision"); 
    List<DetailsPrevision> detailsPrevision = (List<DetailsPrevision>)request.getAttribute("detailsPrevision"); 
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Gestion Solaire - Ecran de Prevision</title>
        <!-- Simple bar CSS -->
        <link rel="stylesheet" href="css/simplebar.css">
        <!-- Icons CSS -->
        <link rel="stylesheet" href="css/feather.css">

        <link rel="stylesheet" href="css/app-light.css" id="lightTheme">
    </head>
    <body class="vertical light">
        <div class="wrapper">
        <nav class="topnav navbar navbar-light mt-2">
            <button type="button" class="navbar-toggler text-muted mt-2 p-0 mr-3 collapseSidebar">
                <i class="fe fe-menu navbar-toggler-icon"></i>
            </button>
            <form class="form-inline mr-auto searchform text-muted">
                <input class="form-control mr-sm-2 bg-transparent border-0 pl-4 text-muted" type="search" placeholder="Type something..." aria-label="Search">
            </form>
        </nav>
        <aside class="sidebar-left border-right bg-white shadow" id="leftSidebar" data-simplebar>
            <a href="#" class="btn collapseSidebar toggle-btn d-lg-none text-muted ml-2 mt-3" data-toggle="toggle">
                <i class="fe fe-x"><span class="sr-only"></span></i>
            </a>
            <nav class="vertnav navbar navbar-light">
            <!-- nav bar -->
                <div class="w-100 mb-4 d-flex">
                    <a class="navbar-brand mx-auto mt-2 flex-fill text-center" href="./index.html">
                        <h3>Solaire</h3>
                    </a>
                </div>
                <ul class="navbar-nav flex-fill w-100 mb-2">
                    <li class="nav-item dropdown">
                        <a href="#dashboard" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle nav-link">
                            <i class="fe fe-home fe-16"></i>
                            <span class="ml-3 item-text">Dashboard</span><span class="sr-only">(current)</span>
                        </a>
                        <ul class="collapse list-unstyled pl-4 w-100" id="dashboard">
                            <li class="nav-item active">
                                <a class="nav-link pl-3" href="./index.jsp"><span class="ml-1 item-text">Default</span></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link pl-3" href="ListeCentre"><span class="ml-1 item-text">Centre</span></a>
                            </li>
                            <li class="nav-item">
                              <a class="nav-link pl-3" href="Coupure"><span class="ml-1 item-text">Coupure</span></a>
                            </li>
                            <li class="nav-item">
                              <a class="nav-link pl-3" href="Prevision"><span class="ml-1 item-text">Prevision</span></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </aside>
        <main role="main" class="main-content">
            <div class="container-fluid">
            <div class="row justify-content-center">
                <div class="col-8">
                    <h2 class="page-title text-center">Prevision de coupure du <%=prevision.getDate() %></h2>
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <p><strong>Temps de coupure estimer : </strong><%=prevision.getCoupure() %></p>
                                    <p><strong>Nombre Moyenne de personne Matin : </strong><%=prevision.getNbrEtuAM() %></p>
                                    <p><strong>Nombre Moyenne de personne Aprés Midi : </strong><%=prevision.getNbrEtuPM() %></p>
                                    <p><strong>Consommation estimer : </strong><%=prevision.getConso_estimer() %></p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card shadow mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <h2>Details</h2>
                                    <table class="table table-hover">
                                        <thead class="thead-dark">
                                          <tr>
                                            <th>Heure Debut</th>
                                            <th>Heure Fin</th>
                                            <th>Consommation</th>
                                            <th>Capacité Batterie</th>
                                            <th>Niveau Lumiere</th>
                                            <th>Puissance Solaire</th>
                                          </tr>
                                        </thead>
                                        <tbody>
                                        <% for(DetailsPrevision dt : detailsPrevision) { %>
                                          <tr>
                                            <td><%=dt.getH_debut() %></td>
                                            <td><%=dt.getH_fin() %></td>
                                            <td><%=dt.getConsommation() %></td>
                                            <td><%=dt.getCapacite_batterie() %></td>
                                            <td><%=dt.getNiv_lum() %></td>
                                            <td><%=dt.getPuissance_solaire() %></td>
                                          </tr>
                                        <% } %>
                                        </tbody>
                                      </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </main>
        </div> <!-- .wrapper -->
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/moment.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/simplebar.min.js"></script>
        <script src='js/jquery.stickOnScroll.js'></script>

        <script src="js/apps.js"></script>
    </body>
</html>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="Controller.VPet"%>
<%@page import="Model.DAO"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- esta linha abaixo é para fazer o conteudo se adaptar a tela, tanto do computador quanto do celular -->
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/game.css">
        <title>Tamagotchi</title>
    </head>
    <body>

        <%
            try{
                Cookie[] cookie = request.getCookies();
                DAO dao = new DAO("lp", "usuario", "pet", "postgres", "root");
                ResultSet result = dao.getCommand("SELECT * from pet where id = " + cookie[1].getValue() +";");
                result.next();
                VPet pet = new VPet(result);

                result = dao.getCommand("SELECT * from pet where id = " + cookie[1].getValue() +";");
                result.next();

                request.setAttribute("saude", result.getInt("saude"));
                request.setAttribute("fome", result.getInt("fome"));
                request.setAttribute("felicidade", result.getInt("felicidade"));
            } catch (Exception ex) {
                System.out.println("Erro ao executar o select a pagina tamagotchi: " + ex);
            }
        %>
        <!-- tudo deve estar dentro desta classe container-fluid -->
        <div class="container-fluid" id="telaTama">

            <div id="menu">
                <div class = "status"> Felicidade
                    <div class="progress">
                    <div id="felicidade" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: ${felicidade}%"></div>
                    </div>
                </div>

                <div class = "status"> Saude
                    <div class="progress">
                    <div id="vida" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: ${saude}%"></div>
                    </div>
                </div>

                <div class = "status">Fome
                    <div class="progress">
                    <div id="fome" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: ${fome}%"></div>
                    </div>
                </div>
            </div>

        <div id = "Personagem">
        </div>
            <div id="menuActions">
                <div id="actions">

                    <button type="button" class="btn btn-outline-dark">Alimentar</button>
                    <button type="button" class="btn btn-outline-dark">Banheiro</button>
                    <button type="button" class="btn btn-outline-dark">Jogar</button>
                    <button type="button" class="btn btn-outline-dark">Curar</button>
                    <button type="button" class="btn btn-outline-dark">Luzes</button>

                </div>
            </div>
        </div>
        <script>
            setTimeout(function(){ location.reload();}, 3000);
        </script>
    </body>
</html>
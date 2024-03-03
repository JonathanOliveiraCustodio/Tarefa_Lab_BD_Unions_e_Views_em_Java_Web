<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Motorista</title>
<style>
  label {
    display: inline-block;
    width: 100px; 
    margin-right: 10px;
  }
</style>
</head>
<body>
    <div>
         <jsp:include page="menu.jsp" />
    </div>
    <br />
     <div align="center" class="container">
     <form action="motorista" method="post">
      <p class="title">
        <b>Motorista</b>
      </p>      
    
      <table>
       <tr>
         <td colspan="3">
          <label for="marca">Código :</label>
          <input class="input_data_id" type="number" min="0" step="1" id="codigo"
          name="codigo" placeholder="Codigo"
          value='<c:out value="${motorista.codigo }"></c:out>'>    
         </td>
          <td>
           <input type="submit" id="botao" name="botao" value="Buscar">
          </td>
       </tr>
        <tr>
         <td colspan="4">
          <label for="marca">Nome :</label>
          <input class="input_data" type="text" id="nome" name="nome" placeholder="Nome"
          value='<c:out value="${motorista.nome }"></c:out>'>  
          </td>
        </tr>
        <tr>
         <td colspan="4">
          <label for="marca">Naturalidade:</label>
          <input class="input_data" type="text" id="naturalidade" name="naturalidade" placeholder="Naturalidade"
          value='<c:out value="${motorista.naturalidade }"></c:out>'>  
          </td>
        </tr>
         <tr>
           <td>
            <input type="submit" id="botao" name="botao" value="Cadastrar">
           </td>
           <td>
            <input type="submit" id="botao" name="botao" value="Alterar">
           </td>
           <td>
            <input type="submit" id="botao" name="botao" value="Excluir">
           </td>
           
          <td>
            <input type="submit" id="botao" name="botao" value="Listar">
           </td>
         
         </tr>
      </table>
      </form>
       </div>
       <br />
        <div align="center">
          <c:if test="${not empty erro }">
           <h2><b><c:out value="${erro }" /></b></h2>          
          </c:if>    
        </div>
      
       <br />
        <div align="center">
          <c:if test="${not empty saida }">
           <h3><b><c:out value="${saida }" /></b></h3>          
          </c:if>    
        </div>
        
        <br />
        <div align="center">
        <c:if test="${not empty motoristas }">
          <table class="table_round">
           <thead>
            <tr>
              <th>Codigo</th>
              <th>Nome</th>
              <th>Naturalidade</th>
            </tr>
            </thead>
             <tbody>
              <c:forEach var="p" items="${motoristas }">
                <tr>
                 <td><c:out value="${p.codigo }" /></td>
                 <td><c:out value="${p.nome }" /></td>
                 <td><c:out value="${p.naturalidade }" /></td>
                </tr>       
              </c:forEach>
             
             </tbody>
          
          </table>       
        </c:if>
       </div>
</body>
</html>
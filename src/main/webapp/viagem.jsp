<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Viagens</title>
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
		<form action="viagem" method="post">
			<p class="title">
				<b>Viagem</b>
			</p>
			<table>
				<tr>
					<td colspan="3"><label for="data">Código Viagem:</label> <input
						class="input_data_id" type="number" min="0" step="1" id="codigo"
						name="codigo" placeholder="Codigo"
						value='<c:out value="${viagem.codigo }"></c:out>'></td>
					<td><input type="submit" id="botao" name="botao"
						value="Buscar"></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Onibus:</label> <select
						class="input_data" id="onibus" name="onibus">
							<option value="0">Escolha um Onibus</option>
							<c:forEach var="o" items="${osonibus }">
								<c:if
									test="${(empty viagem) || (o.placa ne viagem.onibus.placa) }">
									<option value="${o.placa }"><c:out value="${o }" /></option>
								</c:if>
								<c:if test="${o.placa eq viagem.onibus.placa }">
									<option value="${o.placa }" selected="selected"><c:out
											value="${o }" /></option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Motorista:</label> <select
						class="input_data" id="motorista" name="motorista">
							<option value="0">Escolha um Motorista</option>
							<c:forEach var="m" items="${motoristas }">
								<c:if
									test="${(empty viagem) || (m.codigo ne viagem.motorista.codigo) }">
									<option value="${m.codigo }"><c:out value="${m }" /></option>
								</c:if>
								<c:if test="${m.codigo eq viagem.motorista.codigo }">
									<option value="${m.codigo }" selected="selected"><c:out
											value="${m }" /></option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Hora Saída:</label> <input
						class="input_data_id" type="number" min="0" max="23" step="1"
						id="hora_saida" name="hora_saida" placeholder="Hora Saida"
						value='<c:out value="${viagem.hora_saida }"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Hora Chegada:</label> <input
						class="input_data_id" type="number" min="0" max="23" step="1"
						id="hora_chegada" name="hora_chegada" placeholder="Hora Chegada"
						value='<c:out value="${viagem.hora_chegada }"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Partida:</label> <input
						class="input_data" type="text" id="partida" name="partida"
						placeholder="Partida"
						value='<c:out value="${viagem.partida }"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><label for="data">Destino:</label> <input
						class="input_data" type="text" id="destino" name="destino"
						placeholder="Destino"
						value='<c:out value="${viagem.destino }"></c:out>'></td>
				</tr>
				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Excluir"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" id="botao" name="botao"
						value="Descricao Onibus"></td>
					<td colspan="2"><input type="submit" id="botao" name="botao"
						value="Descricao Viagem"></td>
				</tr>
			</table>
		</form>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty erro }">
			<h2>
				<b><c:out value="${erro }" /></b>
			</h2>
		</c:if>
	</div>
	<br />
	<div align="center">
		<c:if test="${not empty saida }">
			<h3>
				<b><c:out value="${saida }" /></b>
			</h3>
		</c:if>
	</div>
	<br />
	<div align="center">
		<c:choose>
			<c:when test="${not empty tipoTabela && tipoTabela eq 'Listar'}">
				<c:if test="${not empty viagens}">
					<table class="table_round">
						<thead>
							<tr>
								<th>Código       </th>
								<th>Ônibus       </th>
								<th>Motorista    </th>
								<th>Hora Saída   </th>
								<th>Hora Chegada </th>
								<th>Partida      </th>
								<th>Destino      </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="v" items="${viagens}">
								<tr>
									<td><c:out value="${v.codigo}" /></td>
									<td><c:out value="${v.onibus}" /></td>
									<td><c:out value="${v.motorista}" /></td>
									<td><c:out value="${v.hora_saida}" /></td>
									<td><c:out value="${v.hora_chegada}" /></td>
									<td><c:out value="${v.partida}" /></td>
									<td><c:out value="${v.destino}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
			<c:when
				test="${not empty tipoTabela && tipoTabela eq 'DescricaoOnibus'}">
				<c:if test="${not empty viagens}">
					<table class="table_round">
						<thead>
							<tr>
								<th style="text-align: center;">Código Viagem </th>
								<th style="text-align: center;">Nome Motorista</th>
								<th style="text-align: center;">Placa         </th>
								<th style="text-align: center;">Marca         </th>
								<th style="text-align: center;">Ano           </th>
								<th style="text-align: center;">Descrição     </th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="v" items="${viagens}">
								<tr>
									<td style="text-align: center;"><c:out value="${v.codigo}" /></td>
									<td style="text-align: center;"><c:out value="${v.motorista.nome}" /></td>
									<td style="text-align: center;"><c:out value="${v.onibus.placa}" /></td>
									<td style="text-align: center;"><c:out value="${v.onibus.marca}" /></td>
									<td style="text-align: center;"><c:out value="${v.onibus.ano}" /></td>
									<td style="text-align: center;"><c:out value="${v.onibus.descricao}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
			<c:when
				test="${not empty tipoTabela && tipoTabela eq 'DescricaoViagem'}">
				<c:if test="${not empty viagens}">
					<table class="table_round">
						<thead>
							<tr>
								<th style="text-align: center;">Código</th>
								<th style="text-align: center;">Placa</th>
								<th style="text-align: center;">Hora Saída</th>
								<th style="text-align: center;">Hora Chegada</th>
								<th style="text-align: center;">Partida</th>
								<th style="text-align: center;">Destino</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="v" items="${viagens}">
								<tr>
									<td style="text-align: center;"><c:out value="${v.codigo}" /></td>
									<td style="text-align: center;"><c:out value="${v.onibus.placa}" /></td>
									<td style="text-align: center;"><c:out value="${v.hora_saida}" /></td>
									<td style="text-align: center;"><c:out value="${v.hora_chegada}" /></td>
									<td style="text-align: center;"><c:out value="${v.partida}" /></td>
									<td style="text-align: center;"><c:out value="${v.destino}" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
		</c:choose>
	</div>
</body>
</html>

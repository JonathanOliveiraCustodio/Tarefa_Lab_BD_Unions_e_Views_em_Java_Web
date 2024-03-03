package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Onibus;
import persistence.GenericDao;
import persistence.OnibusDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/onibus")
public class OnibusServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public OnibusServelet() {
        super();
 
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("onibus.jsp");
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//entrada
				String cmd = request.getParameter("botao");
				String placa = request.getParameter("placa");
				String marca = request.getParameter("marca");
				String ano = request.getParameter("ano");
				String descricao = request.getParameter("descricao");
				
				//saida
				String saida="";
				String erro="";
				Onibus o = new Onibus();
				List<Onibus> osonibus = new ArrayList<>();
				
				if(!cmd.contains("Listar")) {
					o.setPlaca((placa));
				}
				if(cmd.contains("Cadastrar") || cmd.contains("Alterar")){
					o.setMarca(marca);
					o.setAno(Integer.parseInt(ano));
					o.setDescricao(descricao);
				}
				try {
					if (cmd.contains("Cadastrar")) {
						cadastrarOnibus(o);
						saida = "Onibus cadastrado com sucesso";
						o = null;
					}
					if (cmd.contains("Alterar")) {
						alterarOnibus(o);
						saida = "Onibus alterado com sucesso";
						o = null;
					}
					if (cmd.contains("Excluir")) {
						excluirOnibus(o);
						saida = "Onibus excluido com sucesso";
						o = null;
					}
					if (cmd.contains("Buscar")) {
						o = buscarOnibus(o);
					}
					if (cmd.contains("Listar")) {
						osonibus = listarOnibus();
					}
				} catch(SQLException | ClassNotFoundException e) {
					erro = e.getMessage();
				} finally {
					request.setAttribute("saida", saida);
					request.setAttribute("erro", erro);
					request.setAttribute("onibus", o);
					request.setAttribute("osonibus", osonibus);
					
					RequestDispatcher rd = request.getRequestDispatcher("onibus.jsp");
					rd.forward(request, response);
				}
	}


	private void cadastrarOnibus(Onibus o) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao (gDao);
		pDao.inserir(o);
		listarOnibus();
		
	}


	private void alterarOnibus(Onibus o)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao (gDao);
		pDao.atualizar(o);
		listarOnibus();
		
	}

	private void excluirOnibus(Onibus o)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao (gDao);
		pDao.excluir(o);
		listarOnibus();
		
	}

	private Onibus buscarOnibus(Onibus o)throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao (gDao);
		o= pDao.consultar(o);
		return o;
	
	}

	private List<Onibus> listarOnibus()throws SQLException, ClassNotFoundException {
		
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao (gDao);
		List<Onibus> osonibus = pDao.listar();
		
	 return osonibus;
	}

}
package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Motorista;
import model.Onibus;
import model.Viagem;
import persistence.GenericDao;
import persistence.MotoristaDao;
import persistence.OnibusDao;

import persistence.ViagemDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viagem")
public class ViagemServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ViagemServelet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String erro = "";
		List<Onibus> osonibus = new ArrayList<>();
		GenericDao gDao = new GenericDao();
		OnibusDao oDao = new OnibusDao(gDao);

		List<Motorista> motoristas = new ArrayList<>();

		MotoristaDao mDao = new MotoristaDao(gDao);

		try {
			osonibus = oDao.listar();
			motoristas = mDao.listar();

		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();

		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("osonibus", osonibus);
			request.setAttribute("motoristas", motoristas);

			RequestDispatcher rd = request.getRequestDispatcher("viagem.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// entrada
		String cmd = request.getParameter("botao");
		String codigo = request.getParameter("codigo");	
		String onibus = request.getParameter("onibus");
		String motorista = request.getParameter("motorista");
		String hora_saida = request.getParameter("hora_saida");
		String hora_chegada = request.getParameter("hora_chegada");
		String partida = request.getParameter("partida");
		String destino = request.getParameter("destino");
		

		// saida
		String saida = "";
		String erro = "";
		Viagem v = new Viagem();

		List<Onibus> osonibus = new ArrayList<>();
		List<Motorista> motoristas = new ArrayList<>();
		List<Viagem> viagens = new ArrayList<>();

		if (!cmd.contains("Listar") && !cmd.contains("Descricao Onibus") && !cmd.contains("Descricao Viagem")) {
		    v.setCodigo(Integer.parseInt(codigo));
		}

		try {
		    osonibus = listarOnibus();
		    motoristas = listarMotoristas();
		    
		    if (cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
		        Onibus o = new Onibus();
		        o.setPlaca(onibus);
		        o = buscarOnibus(o);
		        v.setOnibus(o);
		        Motorista m = new Motorista();
		        m.setCodigo(Integer.parseInt(motorista));
		        m = buscarMotorista(m);
		        v.setMotorista(m);
		        v.setHora_saida(hora_saida);
		        v.setHora_chegada(hora_chegada);
		        v.setPartida(partida);
		        v.setDestino(destino);
		    }

		    if (cmd.contains("Cadastrar")) {
		        cadastrarViagem(v);
		        saida = "Viagem cadastrada com sucesso";
		        v = null;
		    }
		    if (cmd.contains("Alterar")) {
		        alterarViagem(v);
		        saida = "Viagem alterada com sucesso";
		        v = null;
		    }
		    if (cmd.contains("Excluir")) {
		        excluirViagem(v);
		        saida = "Viagem excluida com sucesso";
		        v = null;
		    }
		    if (cmd.contains("Buscar")) {
		        v = buscarViagem(v);
		    }
		    
		    if (cmd.contains("Listar")) {
		        viagens = listarViagens();
		        request.setAttribute("tipoTabela", "Listar"); 
		    }
		    
		    if (cmd.contains("Descricao Onibus")) {
		        viagens = listarDescricaoOnibus();
		        request.setAttribute("tipoTabela", "DescricaoOnibus");
		    }
		    
		    if (cmd.contains("Descricao Viagem")) {
		        viagens = listarDescricaoViagem();
		        request.setAttribute("tipoTabela", "DescricaoViagem");
		    }
		
		    
		} catch (SQLException | ClassNotFoundException e) {
		    erro = e.getMessage();
		} finally {
		    request.setAttribute("saida", saida);
		    request.setAttribute("erro", erro);
		    request.setAttribute("viagem", v);
		    request.setAttribute("osonibus", osonibus);
		    request.setAttribute("viagens", viagens);
		    request.setAttribute("motoristas", motoristas);
		    RequestDispatcher rd = request.getRequestDispatcher("viagem.jsp");
		    rd.forward(request, response);
		}
	}

	private void cadastrarViagem(Viagem v) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		dDao.inserir(v);

	}

	private void alterarViagem(Viagem v) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		dDao.atualizar(v);

	}

	private void excluirViagem(Viagem v) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		dDao.excluir(v);

	}

	private Viagem buscarViagem(Viagem v) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		v = dDao.consultar(v);
		return v;
	}

	private List<Viagem> listarViagens() throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		List<Viagem> viagens = dDao.listar();
		return viagens;
	}
	
	private List<Viagem> listarDescricaoOnibus() throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		List<Viagem> viagens = dDao.listarOnibus();
		return viagens;
	}
	
	private List<Viagem> listarDescricaoViagem() throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ViagemDao dDao = new ViagemDao(gDao);
		List<Viagem> viagens = dDao.listarViagem();
		return viagens;
	}

	private Onibus buscarOnibus(Onibus o) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao(gDao);
		o = pDao.consultar(o);
		return o;

	}

	private List<Onibus> listarOnibus() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		OnibusDao pDao = new OnibusDao(gDao);
		List<Onibus> osonibus = pDao.listar();

		return osonibus;
	}

	private Motorista buscarMotorista(Motorista m) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		MotoristaDao pDao = new MotoristaDao(gDao);
		m = pDao.consultar(m);
		return m;

	}

	private List<Motorista> listarMotoristas() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		MotoristaDao pDao = new MotoristaDao(gDao);
		List<Motorista> osonibus = pDao.listar();

		return osonibus;
	}

}

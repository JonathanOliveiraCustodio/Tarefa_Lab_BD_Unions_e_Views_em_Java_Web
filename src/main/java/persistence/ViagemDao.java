package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Motorista;
import model.Onibus;

import model.Viagem;

public class ViagemDao implements ICrud<Viagem> {

	private GenericDao gDao;

	public ViagemDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public void inserir(Viagem v) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "INSERT INTO viagem VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, v.getCodigo());
		ps.setString(2, v.getOnibus().getPlaca());
		ps.setInt(3, v.getMotorista().getCodigo());
		ps.setString(4, v.getHora_saida());
		ps.setString(5, v.getHora_chegada());
		ps.setString(6, v.getPartida());
		ps.setString(7, v.getDestino());
		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public void atualizar(Viagem v) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "UPDATE viagem SET onibus = ?, motorista = ?, hora_Saida = ?, "
				+ "hora_Chegada = ?, partida = ?, destino = ? WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, v.getOnibus().getPlaca());
		ps.setInt(2, v.getMotorista().getCodigo());
		ps.setString(3, v.getHora_saida());
		ps.setString(4, v.getHora_chegada());
		ps.setString(5, v.getPartida());
		ps.setString(6, v.getDestino());
		ps.setInt(7, v.getCodigo());
		ps.execute();
		ps.close();
		c.close();
	}

	@Override
	public void excluir(Viagem v) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "DELETE viagem WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, v.getCodigo());
		ps.execute();
		ps.close();
		c.close();

	}

	@Override
	public Viagem consultar(Viagem v) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT v.codigo AS codigoViagem, v.onibus AS placaOnibus, v.motorista AS codigoMotorista, ");
		sql.append("v.hora_Saida AS horaSaida, v.hora_Chegada AS horaChegada, v.partida AS partida, v.destino AS destino, ");
		sql.append("o.marca AS marcaOnibus, m.nome AS nomeMotorista ");
		sql.append("FROM viagem v ");
		sql.append("INNER JOIN onibus o ON v.onibus = o.placa ");
		sql.append("INNER JOIN motorista m ON v.motorista = m.codigo ");
		sql.append("WHERE v.codigo = ?");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ps.setInt(1, v.getCodigo());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Onibus o = new Onibus();
			o.setPlaca(rs.getString("placaOnibus"));
			o.setMarca(rs.getString("marcaOnibus"));


			Motorista m = new Motorista();
			m.setCodigo(rs.getInt("codigoMotorista"));
			m.setNome(rs.getString("nomeMotorista"));
		
			v.setCodigo(rs.getInt("codigoViagem"));
			v.setOnibus(o);
			v.setMotorista(m);
			v.setHora_chegada(rs.getString("horaChegada"));
			v.setHora_saida(rs.getString("horaSaida"));
			v.setPartida(rs.getString("partida"));
			v.setDestino(rs.getString("destino"));
		}
		rs.close();
		ps.close();
		c.close();

		return v;
	}

	@Override
	public List<Viagem> listar() throws SQLException, ClassNotFoundException {
		List<Viagem> viagens = new ArrayList<>();
		Connection c = gDao.getConnection();
	    String sql ="SELECT * FROM v_listar";

		PreparedStatement ps = c.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Viagem viagem = new Viagem();

			Onibus onibus = new Onibus();
			onibus.setPlaca(rs.getString("placaOnibus"));
			onibus.setMarca(rs.getString("marcaOnibus"));

			Motorista motorista = new Motorista();
			motorista.setCodigo(rs.getInt("codigoMotorista"));
			motorista.setNome(rs.getString("nomeMotorista"));

			viagem.setCodigo(rs.getInt("codigoViagem"));
			viagem.setOnibus(onibus);
			viagem.setMotorista(motorista);
			viagem.setHora_saida(rs.getString("horaSaida"));
			viagem.setHora_chegada(rs.getString("horaChegada"));
			viagem.setPartida(rs.getString("partida"));
			viagem.setDestino(rs.getString("destino"));

			viagens.add(viagem);
		}

		rs.close();
		ps.close();
		c.close();

		return viagens;
	}
	
	public List<Viagem> listarViagem() throws SQLException, ClassNotFoundException {
	    List<Viagem> viagens = new ArrayList<>();
	    Connection c = gDao.getConnection();
	    String sql = "SELECT * FROM v_descricao_viagem";

	    PreparedStatement ps = c.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        Viagem viagem = new Viagem();
	        Onibus onibus = new Onibus();
	     //   Motorista motorista = new Motorista();
    	        
	        viagem.setCodigo(rs.getInt("codigoViagem"));
	        onibus.setPlaca(rs.getString("placaOnibus"));
	        viagem.setHora_saida(rs.getString("horaSaida"));
	        viagem.setHora_chegada(rs.getString("horaChegada"));
	        viagem.setPartida(rs.getString("partida"));
	        viagem.setDestino(rs.getString("destino"));	        
	        viagem.setOnibus(onibus);

	        viagens.add(viagem);
	    }
	    rs.close();
	    ps.close();
	    c.close();

	    return viagens;
	}

	public List<Viagem> listarOnibus()throws SQLException, ClassNotFoundException {
		 List<Viagem> viagens = new ArrayList<>();
		    Connection c = gDao.getConnection();
		    String sql = "SELECT * FROM v_descricao_onibus";

		    PreparedStatement ps = c.prepareStatement(sql);
		    ResultSet rs = ps.executeQuery();

		    while (rs.next()) {
		        Viagem viagem = new Viagem();
		        Onibus onibus = new Onibus();
		        Motorista motorista = new Motorista();
	    	        
		        viagem.setCodigo(rs.getInt("codigoViagem"));
		        motorista.setNome(rs.getString("nomeMotorista"));
		        onibus.setPlaca(rs.getString("placaOnibus"));
		        onibus.setMarca(rs.getString("marcaOnibus"));
		        onibus.setAno(rs.getInt("anoOnibus"));
		        onibus.setDescricao(rs.getString("descricaoOnibus"));
		        viagem.setOnibus(onibus);
		        viagem.setMotorista(motorista);
		        viagens.add(viagem);
		    }
		    rs.close();
		    ps.close();
		    c.close();

		    return viagens;
	}
	}
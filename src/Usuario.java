import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Usuario {
	private String nome;
	private String email;
	private int idade;
	private double altura;
	
	public Usuario(String nome, String email, int idade, double altura) {
		this.nome = nome;
		this.email = email;
		this.idade = idade;
		this.altura = altura;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
	
	public void salvarUsuario(String path) {
		String strDados = path + "\\dados";
		try {
			Path dadosPath = Paths.get(strDados);
			Files.createDirectories(dadosPath);
			
			File[] registros = dadosPath.toFile().listFiles(File::isFile);
			int novoRegistro = registros != null ? registros.length + 1 : 1;
	
			String strNovoUsuario = strDados + "\\" + novoRegistro + "-" + this.nome.toUpperCase().replaceAll(" ", "") + ".txt";

			try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(strNovoUsuario))){
				bufferedWriter.write(this.toString());
			} catch (Exception e) {
				System.out.println("Erro ao criar novo arquivo: " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Erro ao ler os arquivos: " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return nome + "\n" + email + "\n" + idade + "\n" + altura + "\n";
	}
}

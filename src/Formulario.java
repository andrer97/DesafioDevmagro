import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Formulario {
	private static Formulario instancia;
	private List<String> perguntas;
	private String path;

	private Formulario(String path) {
		this.perguntas = new ArrayList<String>();
		this.path = path;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path))) {
			String line = bufferedReader.readLine();
			while (line != null) {
				perguntas.add(line);
				line = bufferedReader.readLine();
			}
		} catch (Exception e) {
			System.out.println("Erro ao abrir o formulario: " + e.getMessage());
		}
	}

	public static Formulario getFormulario(String path) {
		if (instancia == null) {
			instancia = new Formulario(path);
		}
		return instancia;
	}
	
	public List<String> getPerguntas() {
		return perguntas;
	}
	
	public void cadastrarPergunta(String pergunta) {
		try {
			String novaPergunta = pergunta;
			perguntas.add(novaPergunta);
			atualizarArquivo();
		} catch (Exception e) {
			System.out.println("Nao foi possivel cadastrar a pergunta: " + e.getMessage());
		}
	}

	public void deletarPergunta(Scanner sc) {
		System.out.println("Lista de perguntas: ");
		int indice = 0;
		for (String pergunta : perguntas) {
			indice++;
			System.out.println(indice + " - " + pergunta);
		}
		System.out.println("Qual pergunta deseja remover? ");
		int id = sc.nextInt();
		id--;
		if (id >= 0 && id < 4) {
			System.out.println("Nao e possivel deletar essa pergunta! ");
		}
		else {
			try {
				perguntas.remove(id);
				atualizarArquivo();
			} catch (Exception e) {
				System.out.println("Nao foi possivel deletar essa pergunta: " + e.getMessage());
			}
		}
	}
	
	private void atualizarArquivo() {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.path))) {
			for (String pergunta : perguntas) {
				bufferedWriter.write(pergunta + "\n");
			}
		} catch (Exception e) {
			System.out.println("Nao foi possivel atualizar o arquivo. ");
		}
	}
}

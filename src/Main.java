import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		String path = "C:\\projetos\\DesafioDevmagro\\files";
		String strFormulario = path + "\\formulario.txt";
		Formulario formulario = Formulario.getFormulario(strFormulario);
		Scanner sc = new Scanner(System.in);
		

		System.out.println("1 - Cadastrar o usuário\n2 - Listar todos usuários cadastrados\n3 - Cadastrar nova pergunta no formulário\n4 - Deletar pergunta do formulário\n5 - Pesquisar usuário por nome");
		int opcMenu = sc.nextInt();
		sc.nextLine();
		switch (opcMenu) {
		case 1:
			cadastrarUsuario(formulario, sc, path);
			break;
		case 2:
			listarUsuarios(path);
			break;
		case 3:
			System.out.println("Digite a pergunta que deseja adicionar: ");
			String pergunta = sc.nextLine();
			formulario.cadastrarPergunta(pergunta);
			break;
		case 4:
			formulario.deletarPergunta(sc);
			break;
		case 5:
			pesquisarUsuario(sc, path);
		default:
			break;
		}

		sc.close();
	}

	private static void cadastrarUsuario(Formulario formulario, Scanner sc, String path) {
		List<Usuario> listaUsuario = carregarUsuarios(path);
		
		try {
			List<String> perguntas = formulario.getPerguntas();

			String nome = null;
			String email = null;
			int idade = 0;
			double altura = 0.0;

			int indice = 0;
			for (String pergunta : perguntas) {
				indice++;
				System.out.println(indice + " - " + pergunta);
				switch (indice) {
				case 1:
					nome = sc.nextLine();
					if (nome.length() < 10) {
						throw new IllegalArgumentException("O nome deve ter no mínimo 10 caracteres.");
					}
					break;
				case 2:
					email = sc.nextLine();
					if (!email.contains("@")) {
						throw new IllegalArgumentException("O email deve ter '@'.");
					}
					for (Usuario usuario : listaUsuario) {
						if (email.equals(usuario.getEmail())) {
							throw new Exception("Esse email ja está cadastrado.");
						}
					}
					break;
				case 3:
					if (sc.hasNextInt()) {
						idade = sc.nextInt();
						sc.nextLine();
						if (idade < 18) {
							throw new IllegalArgumentException("A idade mínima deve ser 18.");
						}
					}
					else {
						throw new IllegalArgumentException("Idade deve ser um número inteiro.");
					}
					break;
				case 4:
						if (sc.hasNextDouble()) {
							altura = sc.nextDouble();
						}
						else {
							throw new IllegalAccessException("A altura deve ser um número com vírgula.");
						}
					break;
				}
			}
			Usuario user = new Usuario(nome, email, idade, altura);
			System.out.println(user);
			user.salvarUsuario(path);
			
		} catch (Exception e) {
			System.out.println("Erro ao ler dados: " + e.getMessage());
		}
	}

	private static void listarUsuarios(String path) {
		List<Usuario> listaUsuario = carregarUsuarios(path);
		
		if (listaUsuario != null) {
			int indice = 0;
			for (Usuario usuario : listaUsuario) {
				indice++;
				System.out.println(indice + " - " + usuario.getNome());
			}
		}
		else {
			System.out.println("Nenhum registro encontrado. ");
		}
    }
	 
	private static List<Usuario> carregarUsuarios(String path){
        try {
            String strDados = path + "\\dados";
            Path dadosPath = Paths.get(strDados);
            List<Usuario> usuarios = new ArrayList<Usuario>();

            if (!Files.exists(dadosPath) || !Files.isDirectory(dadosPath)) {
                System.out.println("Nenhuma pasta de dados encontrada.");
                return null;
            }

            File[] registros = dadosPath.toFile().listFiles(File::isFile);

            if (registros != null) {
                for (File registro : registros) {
        	        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(registro))) {
        	        	
        	        	String nome = null;
        				String email = null;
        				int idade = 0;
        				double altura = 0.0;

        	            String linha = bufferedReader.readLine();
        	            if (linha != null) {
        	                nome = linha;
        	                linha = bufferedReader.readLine();
        	                email = linha;
        	                linha = bufferedReader.readLine();
        	                idade = Integer.parseInt(linha);
        	                linha = bufferedReader.readLine();
        	                altura = Double.parseDouble(linha);
        	                linha = bufferedReader.readLine();
            	            Usuario user = new Usuario(nome, email, idade, altura);
            	            usuarios.add(user);
        	            } else {
        	                System.out.println("Arquivo vazio: " + registro.getName());
        	            }
        	        } catch (IOException e) {
        	            System.out.println("Erro ao ler o arquivo " + registro.getName() + ": " + e.getMessage());
        	        }
                }
                return usuarios;
            }
            else {
                System.out.println("Nenhum dado encontrado na pasta de dados.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }
		return null;
	 }

	private static void pesquisarUsuario(Scanner sc, String path) {
		List<Usuario> listaUsuario = carregarUsuarios(path);
		
		System.out.println("Digite o nome que deseja pesquisar.");
		String nome = sc.nextLine();
		
		for (Usuario usuario : listaUsuario) {
			if (usuario.getNome().contains(nome)) {
				System.out.println("Nome: " + usuario.getNome() + ", Email: " + usuario.getEmail() + ", Idade: " + usuario.getIdade() + ", Altura: " + usuario.getAltura());
			}
		}
	}
}

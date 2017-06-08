package pc.ejemplos2.sockets;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

class ClienteEcoTexto {

	public static void main(String[] args) {
		ClienteEcoTexto cliente = new ClienteEcoTexto();
		cliente.ejecutar();
	}

	private void ejecutar() {
		try {
			// Crear socket cliente y establecer conexion
			// con un timeout de 5 segundos
			Socket socketConexion = new Socket();
			socketConexion.connect(new InetSocketAddress("localhost", 2020),
				5000);
			// Fijar timeout de lectura en 2 segundos
//			socketConexion.setSoTimeout(2000);
			PrintWriter out = null;
			BufferedReader in = null;
			try {
				System.out.println("Cliente> Establecida conexion");

				// Obtener flujos de salida y entrada
				OutputStream outStream = socketConexion.getOutputStream();
				InputStream inStream = socketConexion.getInputStream();

				// Crear flujos de escritura y lectura
				out = new PrintWriter(outStream);
				System.out.println("Cliente> Obtenido flujo de escritura");
				in = new BufferedReader(new InputStreamReader(inStream));
				System.out.println("Cliente> Obtenido flujo de lectura");

				// Crear flujo para leer de la entrada estandar
				BufferedReader inStd = new BufferedReader(
						new InputStreamReader(System.in));

				// Escribir y leer en los flujos
				boolean salir = false;
				while (!salir) {
					System.out.print("Cliente> Escriba una linea: ");
					String lineaEnviar = inStd.readLine();
					out.println(lineaEnviar);
					out.flush();
					String lineaRecibir = in.readLine();
					if (lineaRecibir == null) {
						salir = true;
					} else {
						System.out.println("Cliente> Recibida linea = " +
								lineaRecibir);
					}
				}
			} finally {
				// Cerrar flujos
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				// Cerrar socket de la conexion
				socketConexion.close();
				System.out.println("Cliente> Fin de conexion");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

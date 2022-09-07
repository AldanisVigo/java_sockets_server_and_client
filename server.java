
import java.net.Socket;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;

class Server {
    //Create a socket, server socket and it's input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    /*
        The server needs to know what port to sit on and listen for incoming 
        requests, therefore we must provide the server the port as a constructor
        parameter
    */
    public Server(int port){
        //Attempt to start the server on the specified port
        //and wait for incoming connections and requests
        try{
            //Establish a server socket connection on the specified port
            server = new ServerSocket(port);

            //Let the user know the server has started listening on the specified port
            System.out.printf("Server listening on port %d\n\n\n", port);

            //Create a regular socket from calling the server socket's accept method
            /*
                Source: https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
                
                Method: public Socket accept() throws IOException
                
                Explanation: Listens for a connection to be made to this socket and accepts it. 
                The method blocks until a connection is made.
                A new Socket s is created and, if there is a security manager, 
                the security manager's checkAccept method is called 
                with s.getInetAddress().getHostAddress() and s.getPort() as 
                its arguments to ensure the operation is allowed. 
                This could result in a SecurityException.

            */
            socket = server.accept();

            //Assing the input data stream to the input stream of the regular socket (not the server socket!!!)
            //By converting the regular socket's input stream, to a buffered input stream
            BufferedInputStream bufferedSocketInputStream = new BufferedInputStream(socket.getInputStream());
            
            //And converting the buffered input stream from the regular socket
            //to a data input stream and assign it to the input stream
            in = new DataInputStream(bufferedSocketInputStream);

            //We need a string to store the input from the input stream when requests come in
            String incomingValue = "";
        
            //As long as the incoming value is not "Over"
            while(!incomingValue.equals("Over")){ //Continue iterating through the input
                try{ //Attempt to read the next input
                    
                    //By calling .readUTF() on the input stream
                    incomingValue = in.readUTF();

                    //And display it on the server's terminal
                    System.out.println("CLIENT:" + incomingValue);

                    //Responding to the client
                    

                }catch(Exception err){ //Catch any exceptions that might occur
                    //And display an error message on the server's terminal
                    System.out.println(err.getMessage());
                }
            }

            //As soon as we break out of the while loop because "Over" was transmitted
            //Close the socket connection
            socket.close();

            //And then close the input data stream
            in.close();

        }catch(Exception err){ //If there are any exceptions catch them
            
            //And display an error message on the server's terminal
            System.out.println(err.getMessage());
        }
    }

    public static void main(String[] args){
        //Create a server and start listening for connections on port 5000
        Server server = new Server(5005);

    }
}
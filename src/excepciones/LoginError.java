package excepciones;

public class LoginError extends Exception {
    private int codigoError;

    public LoginError(int codigoError){
        super();
        this.codigoError=codigoError;
    }

    @Override
    public String getMessage(){

        String mensaje="";

        switch(codigoError){
            case 111:
                mensaje="Error. Login incorrecto";
                break;
            case 222:
                mensaje="Error. Contraseña incorrecta";
                break;
        }

        return mensaje;

    }
}

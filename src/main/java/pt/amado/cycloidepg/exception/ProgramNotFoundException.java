package pt.amado.cycloidepg.exception;

public class ProgramNotFoundException extends CycloidGeneralException{

    public ProgramNotFoundException(long programId) {
        super(String.format(
                "There is no program registered with id %s",
                programId
        ));
    }
}

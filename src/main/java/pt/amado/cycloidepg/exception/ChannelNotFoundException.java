package pt.amado.cycloidepg.exception;

public class ChannelNotFoundException extends CycloidGeneralException{

    public ChannelNotFoundException(long channelId) {
        super(String.format(
                "There is no channel registered with id %s",
                channelId
        ));
    }
}

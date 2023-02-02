package com.itzilly.shadowOverlay.objects;

import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;

import java.io.IOException;

public class NickedPlayer extends OverlayPlayer {
    public NickedPlayer(String name) throws APIException, IOException, InvalidPlayerException {
        super(name);
        super.setNicked(true);
    }
}

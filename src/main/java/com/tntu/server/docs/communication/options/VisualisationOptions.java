package com.tntu.server.docs.communication.options;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VisualisationOptions {

    @Value("${visualisation.extensions}")
    public List<String> convertableExtensions;

    @Value("${cash-live.time.hour}")
    public long cashLivingTime;

}

package com.tntu.server.docs.communication.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisualisationOptions {

    @Value("${visualisation.extensions}")
    public List<String> convertableExtensions;

    @Value("${cash-live.time.hour}")
    public long cashLivingTime;

}

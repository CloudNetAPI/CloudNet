/*
 * Copyright (c) Tarek Hosni El Alaoui 2017
 */

package de.dytanic.cloudnet.lib.server.advanced;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Created by Tareko on 14.10.2017.
 */
@Getter
@AllArgsConstructor
public class DevService {

    private UUID uniqueId;

    private boolean enabled;

}
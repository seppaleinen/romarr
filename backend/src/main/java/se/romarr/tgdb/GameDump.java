package se.romarr.tgdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GameDump(int code, String status, @JsonProperty("last_edit_it") String lastEditId, Data data) {
}

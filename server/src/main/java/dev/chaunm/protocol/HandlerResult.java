package dev.chaunm.protocol;

import java.util.List;

public record HandlerResult<T>(
        List<Long> responseReceivers,
        Response<T> response
) {
}

package ca.uptoeleven.status.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ListHolder<T> {

	private final List<T> data;

}

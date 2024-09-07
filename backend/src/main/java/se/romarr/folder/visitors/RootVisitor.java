package se.romarr.folder.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class RootVisitor extends SimpleFileVisitor<Path> {
	private final List<? extends CustomVisitor> fileVisitors;

	public RootVisitor(List<? extends CustomVisitor> fileVisitors) {
		this.fileVisitors = fileVisitors;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		if (dir.toString().endsWith("roms")) {
			return FileVisitResult.CONTINUE;
		}
		return fileVisitors.stream()
				.filter(visitor -> visitor.isRelated(dir))
				.findFirst()
				.map(visitor -> {
					try {
						Files.walkFileTree(dir, visitor);
						return FileVisitResult.SKIP_SUBTREE;
					} catch (IOException e) {
						System.err.println("Could not traverse: " + dir + " : " + e.getMessage());
						return FileVisitResult.CONTINUE;
					}
				})
				.orElseGet(() -> {
					System.out.println("Ignoring: " + dir);
					return FileVisitResult.SKIP_SUBTREE;
				});
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		System.out.println("Visiting: " + file.toString());
		return FileVisitResult.SKIP_SIBLINGS;
	}
}

package se.romarr.scanner.folder.visitors;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class CustomVisitor extends SimpleFileVisitor<Path> {
	public abstract FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs);
	public abstract FileVisitResult visitFile(Path file, BasicFileAttributes attrs);

	public abstract boolean isRelated(Path dir);
}

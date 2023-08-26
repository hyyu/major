package extensions

import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

private fun DependencyHandlerScope.implementation(dependency: String) = "implementation"(dependency)
private fun DependencyHandlerScope.implementation(dependency: ProjectDependency) = "implementation"(dependency)

fun DependencyHandlerScope.implementations(vararg dependencies: Any) =
    dependencies.forEach {
        add("implementation", it)
    }

fun DependencyHandlerScope.implementationProjects(vararg dependencies: String) {
    dependencies.map { project(it) }.forEach { implementation(it) }
}

fun DependencyHandlerScope.kapt(vararg dependencies: Any) =
    dependencies.forEach {
        add("kapt", it)
    }

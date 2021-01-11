plugins {
    id ("fabric-loom") version "0.5-SNAPSHOT"
}

version = properties["mod_version"] as String
group = properties["maven_group"] as String

base {
    archivesBaseName = properties["archives_base_name"] as String
}

val fabricVersion = properties["fabric_version"] as String

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")

        setOf(
            "fabric-api-base",
            "fabric-command-api-v1"
        ).forEach {
            modImplementation(fabricApi.module(it, fabricVersion))
        }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.processResources{
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
		expand("version" to project.version)
	}
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
plugins {
    id("java")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.github.librepdf:openpdf:1.3.30")
    implementation("org.apache.velocity:velocity:1.7")
    implementation("com.itextpdf:itextpdf:5.5.13.1")
    implementation("org.xhtmlrenderer:flying-saucer-core:9.1.20")
    implementation("org.xhtmlrenderer:flying-saucer-pdf-openpdf:9.1.20")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("org.apache.directory.studio:org.apache.commons.io:2.4")
    implementation("com.itextpdf.tool:xmlworker:5.5.13.1")
    implementation("com.openhtmltopdf:openhtmltopdf-core:1.0.6")
    implementation("com.openhtmltopdf:openhtmltopdf-pdfbox:1.0.6")

}

tasks.test {
    useJUnitPlatform()
}
plugins {
    // Apply the java-library plugin for API and implementation separation.
    id("com.stulsoft.rxjava.java-library-conventions")
}

dependencies {
    implementation(project(":generator"))
}

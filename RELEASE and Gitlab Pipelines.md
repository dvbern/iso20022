# Release

This library uses the [default branch](https://gitlab.dvbern.ch/components/release/-/tree/main/templates/default-branch?ref_type=heads) component to manage the release process.

A new release is triggered through the [Release Manager](https://release-manager.apps.apollo.ocp.dvbern.ch/).

Before creating a new release, update `CHANGELOG.md` accordingly and pick a releas version accodring to SEMVER.


# Pipelines

## Default Branch Pipeline

Builds and deploys the maven project to maven central on every push in the default branch.

Therefore, the version number in the `pom.xml` files should typically be a SNAPSHOT version.

## Merge Request Pipeline

Builds the maven project with the verify goal, to make sure the code is compilable and passes all tests.

## Release Pipeline

The release pipeline is responsible for updating the version number in the `pom.xml` files and creating a new tag in the Git repository.

It is created by triggering a release through the [Release Manager](https://release-manager.apps.apollo.ocp.dvbern.ch/).

## Tag Pipeline

The tag pipeline is responsible for publishing a new version of the library to Maven Central and publishing the source code to github.

It is automatically created by the release pipeline.

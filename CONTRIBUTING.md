## Contributing

First off, thank you for considering contributing to the library lib-iso20022!
### 1. Where do I go from here?

If you've noticed a bug or have a question that doesn't belong on the
[wiki](https://github.com/dvbern/lib-iso20022/wiki), then
[search the issue tracker](https://github.com/dvbern/lib-iso20022/issues)
to see if someone else in the community has already created a ticket regarding your issue.
If not, feel free to go ahead and [make one](https://github.com/dvbern/lib-iso20022/issues/new)!

### 2. Did you find a bug?

* **Ensure the bug was not already reported** by searching on GitHub under [Issues](https://github.com/dvbern/lib-iso20022/issues).

* If you're unable to find an open issue addressing the problem, [open a new one](https://github.com/dvbern/lib-iso20022/issues/new). 
Be sure to include a **title and clear description**, as much relevant information as possible, 
and a **code sample** or an **executable test case** demonstrating the expected behavior that is not happening.

### 3. I can fix this!

At this point, you're ready to make your changes! Feel free to ask for help!

If this is something you think you can fix, then
[fork lib-iso20022](https://help.github.com/articles/fork-a-repo)
and create a branch with a descriptive name.

A good branch name would be (where issue #325 is the ticket you're working on):

```sh
git checkout -b 325-add-mynewchanges123
```

### 4. Code of Conduct

By beeing part of our development team and community, please abide the rules in our [Code of Conduct](CODE_OF_CONDUCT.md)! 
Further information on how to report inapropriate behaviour by others, check the Enforcements-Section in said Code Of Conduct. Thank you for beeing a nice person!

### 5. Code Styles, Guildlines & Inspections

Since we're trying to keep the code structured, readable and standardised, please check out and implement our Code Styles & Inspections if you are using IntelliJ IDEA.

[Code Styles](https://raw.githubusercontent.com/dvbern/codestyles/master/src!IDE-settings!IntelliJ!DVBern-Conventions-2017-05-29.xml)(Add Scheme under Project Settings > Editor > Code Style)

[Inspections](https://raw.githubusercontent.com/dvbern/codestyles/master/src!IDE-settings!IntelliJ!DVBern_Inspections_2017_05_19.xml)(Add Profile under Project Settings > Editor > Inspections)

### 6. Make a Pull Request

At this point, you should switch back to your master branch and make sure it's
up to date with the master branch:

```sh
git remote add upstream git@github.com/dvbern/lib-iso20022.git
git checkout master
git pull upstream master
```

Then update your feature branch from your local copy of master, and push it!

```sh
git checkout 325-add-mynewchanges123
git rebase master
git push --set-upstream origin 325-add-mynewchanges123
```

Finally, go to GitHub and
[make a Pull Request](https://help.github.com/articles/creating-a-pull-request)


### 7. Keeping your Pull Request updated

If a maintainer asks you to "rebase" your PR, they're saying that a lot of code
has changed, and that you need to update your branch so it's easier to merge.

To learn more about rebasing in Git, there are a lot of
[good](http://git-scm.com/book/en/Git-Branching-Rebasing)
[resources](https://help.github.com/articles/interactive-rebase),
but here's the suggested workflow:

```sh
git checkout 325-add-mynewchanges123
git pull --rebase upstream master
git push --force-with-lease 325-add-mynewchanges123
```

### 8. Merging a Pull Request (maintainers only)

A Pull Reques can only be merged into master by a maintainer if:

* It is passing the tests.
* It has been approved by at least two maintainers. If it was a maintainer who
  opened the Pull Reques, only one extra approval is needed.
* It has no requested changes.
* It is up to date with current master.

Any maintainer is allowed to merge a Pull Reques if all of these conditions are
met.

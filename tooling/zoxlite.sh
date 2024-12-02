#!/bin/sh

_zl_GAM_PREFIX="/game"
_zl_DOCS_PREFIX="/CodeDocs"
_zl_TOOLING_PREFIX="/tooling"

# There is no POSIX way for a sourced sh script to know its name/path
# shellcheck disable=SC2128
if [ -z "$BASH_SOURCE" ]; then
    _zl_WD=$(dirname -- "$(readlink -f -- "$0")")
else
    _zl_WD=$(dirname -- "$(readlink -f -- "$BASH_SOURCE")")
fi
# shellcheck disable=SC2317 # ./script.sh -> exit; source ./script.sh -> return
git -C "$_zl_WD" rev-parse --is-inside-work-tree >/dev/null 2>&1 || { echo "$(basename -- "$(readlink -f -- "$0")"): Not installed in or executed from within a git repository."; return 1 2>/dev/null; exit 1; }

_zl_BASE=$(git -C "$_zl_WD" rev-parse --show-toplevel)
_zl_pkgpath=$(sed -n "s/.*identifier\.set(\"\([a-zA-Z0-9\.]\+\)\" + appName).*/\1/p" "${_zl_BASE}${_zl_GAM_PREFIX}/lwjgl3/build.gradle" | sed "s/\./\//g; 1q")
_zl_GAM="${_zl_BASE}${_zl_GAM_PREFIX}/core/src/main/java/${_zl_pkgpath}"
_zl_LUWBGWB="${_zl_BASE}${_zl_GAM_PREFIX}/lwjgl3/src/main/java/${_zl_pkgpath}lwjgl3/"
_zl_DOC="${_zl_BASE}${_zl_DOCS_PREFIX}"
_zl_TOOL="${_zl_BASE}${_zl_TOOLING_PREFIX}"
# shellcheck disable=SC2139     # Expansion is intentional
alias togame="cd \"$_zl_GAM\""
# shellcheck disable=SC2139
alias tolwjgl="cd \"$_zl_LUWBGWB\""
# shellcheck disable=SC2139
alias todocs="cd \"$_zl_DOC\""

fgrymd() {
    command -v fzf >/dev/null || { echo "fzf not installed"; return 2; }
    cd "$(find "$_zl_GAM" "$_zl_TOOL" "$_zl_LUWBGWB" "$_zl_DOC" -type d | fzf -e -0 --scheme=path -n "-1" -d "/")" || return
}

fgrym() {
    command -v fzf >/dev/null || { echo "fzf not installed"; return 2; }
    [ -z "$EDITOR" ] && {
        for cmd in nvim vim vi nano; do command -v "$cmd" >/dev/null && EDITOR=$cmd && break; done;
        [ -z "$EDITOR" ] && { echo "No editors available"; return 2; };
    }
    find "$_zl_GAM" "$_zl_TOOL" "$_zl_LUWBGWB" "$_zl_DOC" -type f | fzf -e -0 --scheme=path -n "-1" -d "/" --print0 | xargs -0 -I {} "$EDITOR" "{}"
}

# open all uncommitted and untracked files
vwip() {
    _zl_WIP=$(git -C "$_zl_BASE" diff --name-only HEAD)
    _zl_UNTRACKED=$(git -C "$_zl_BASE" ls-files -o --exclude-standard)
    if [ -n "$_zl_WIP" ] || [ -n "$_zl_UNTRACKED" ]; then
        [ -z "$EDITOR" ] && {
            for cmd in nvim vim vi nano; do command -v "$cmd" >/dev/null && EDITOR=$cmd && break; done;
            [ -z "$EDITOR" ] && { echo "No editors available"; return 2; };
        }
        printf "%s\n" "$_zl_WIP" "$_zl_UNTRACKED" | sed "s|^|$_zl_BASE/|" | xargs -d "\n" -r "$EDITOR" --
    else
        echo "No WIP files."
    fi
    unset _zl_WIP _zl_UNTRACKED
}

alias fgrum=fgrym
alias fgrumd=fgrymd

unset _zl_GAM_PREFIX _zl_pkgpath

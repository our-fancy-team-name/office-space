const cache = {};

/**
 * Generates a short id.
 *
 */
export function id(): string {
  // tslint:disable-next-line: no-bitwise
  let newId = ('0000' + ((Math.random() * Math.pow(36, 4)) << 0).toString(36)).slice(-4);

  newId = `a${newId}`;

  // ensure not already used
  if (!cache[newId]) {
    cache[newId] = true;
    return newId;
  }

  return id();
}

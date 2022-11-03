export const UPDATE = "UPDATE";
export const INSERT = "INSERT";

export function increment(data) {
  return {
    type: UPDATE,
    data: data,
  };
}

export function decrement(data) {
  return {
    type: INSERT,
    data: data,
  };
}

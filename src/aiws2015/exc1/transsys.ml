module type Transsys =
sig
  type state
  val initstates : state list
  val next : state -> state list
end

module Whileprog : Transsys =
struct
  type state = int
  let initstates = [0]
  let next x = if x < 100 then [x+1] else []
end

module Reachstates (T : Transsys) =
struct
  module Mystateset =
    Set.Make(struct
               type t = T.state
	       let compare = Pervasives.compare
	     end)
  
  (* f : T.state set -> T.state set *)
  let f s =
    Mystateset.union
      (Mystateset.of_list T.initstates)
      (Mystateset.fold
	 (fun e acc ->
	   Mystateset.union
	     (Mystateset.of_list (T.next e))
	     acc)
	 s
	 Mystateset.empty)
end